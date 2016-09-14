package sync.process;

/**
 * @author 洪 qq:2260806429
 */
public class SimpleProcessListener implements IProcessListener {
	private CancelControl cancelControl;
	private String name = "";
	private long totalSize;
	private long workSize = 0;

	public SimpleProcessListener() {
		this(null);
	}

	public SimpleProcessListener(CancelControl cancelControl) {
		this(cancelControl, 0);
	}

	public SimpleProcessListener(CancelControl cancelControl, long totalSize) {
		this.cancelControl = cancelControl;
		this.totalSize = totalSize;
	}

	@Override
	public void work(long size) {
		workSize += size;
	}

	@Override
	public boolean isFinish() {
		return (totalSize != 0 && totalSize == workSize) || isCancel();
	}

	@Override
	public double getPercent() {
		return ((int) (workSize * 1.0 / totalSize * 10000)) * 1.0 / 100;
	}

	@Override
	public boolean isCancel() {
		return cancelControl != null && cancelControl.isCancel();
	}

	@Override
	public void updateTotalSize(long totalSize) {
		this.totalSize = totalSize;
		touchPrint();
	}

	@Override
	public void print(String name) {
		this.name = name;
		touchPrint();
	}

	private void touchPrint() {
		if (null != name && 0 != name.length() && 0 != this.totalSize) {
			new ProcessPrinter(name).start(this);
		}
	}

}
