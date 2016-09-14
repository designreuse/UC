package sync.main;

import sync.core.FSConfig;
import sync.core.SyncFolderInfo;
import sync.core.SyncItem;
import sync.process.CancelControl;
import sync.process.IProcessListener;
import sync.process.SimpleProcessListener;
import sync.util.FileUtils;
import sync.util.LogHelper;
import sync.util.TimeUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 程序入口
 *
 * @author 洪 qq:2260806429
 */
public class Main {

    public static void main(String argv[]) throws IOException {
        File sourceFile;
        File targetFile;
        if (argv.length == 2) {
            sourceFile = new File(argv[0]);
            targetFile = new File(argv[1]);
        } else {
            LogHelper.info("请传入参数");
            return;
        }
        if (!sourceFile.exists()) {
            LogHelper.info("源对象不存在:" + sourceFile.getAbsolutePath());
            return;
        }

        doSync(sourceFile, targetFile);
    }

    private static void doSync(final File sourceFile, final File targetFile) throws IOException {
        long start = System.currentTimeMillis();
        try {
            sync(sourceFile, targetFile);
        } finally {
            long cost = System.currentTimeMillis() - start;
            LogHelper.info("同步完成耗时:" + TimeUtil.printCostTime(cost));
        }
    }

    public static void sync(File sourceFile, File targetFile) throws IOException {
        LogHelper.info("Sync from \"" + sourceFile.getAbsolutePath() + "\" TO \"" + targetFile.getAbsolutePath() + "\"");

        final CancelControl cancelControl = new CancelControl();
        final IProcessListener listenerSource = new SimpleProcessListener(cancelControl);
        listenerSource.print("source scan");
        File cacheFile = new File(sourceFile, FSConfig.CACHE_ID_FILE);// 不存在
        if (cacheFile.isDirectory()) {
            FileUtils.del(cacheFile);
        }
        final SyncFolderInfo source = new SyncFolderInfo(sourceFile);
        final SyncFolderInfo target = new SyncFolderInfo(targetFile);
        final IProcessListener listenerTarget = new SimpleProcessListener(cancelControl);

        listenerTarget.print("target scan");

        final int[] finishCount = new int[1];
        new Thread() {
            public void run() {
                try {
                    source.scan(listenerSource);
                } catch (IOException e) {
                    LogHelper.error(e);
                    cancelControl.cancel = true;
                } finally {
                    finishCount[0]++;
                }
            }
        }.start();

        new Thread() {
            public void run() {
                try {
                    target.scan(listenerTarget);
                } catch (IOException e) {
                    LogHelper.error(e);
                    cancelControl.cancel = true;
                } finally {
                    finishCount[0]++;
                }
            }
        }.start();

        while (finishCount[0] != 2) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LogHelper.error(e);
            }
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            LogHelper.error(e);
        }

        long size = 0;
        List<SyncItem> items = source.sync(target);
        for (SyncItem item : items) {
            size += item.getSyncSize();
        }
        LogHelper.info("total sync size:" + (size * 100 / (1024 * 1024) * 1.0 / 100) + "M");
        createTargetFile(targetFile);

        final IProcessListener listenerSync = new SimpleProcessListener(cancelControl, size);
        listenerSync.print("sync");
        for (SyncItem item : items) {
            if (listenerSync.isCancel()) {
                break;
            }
            item.sync(sourceFile, targetFile, listenerSync);
        }
    }

    private static void createTargetFile(final File targetFile) {
        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        if (!targetFile.exists()) {
            throw new RuntimeException("创建目标文件夹失败");
        }
    }

}
