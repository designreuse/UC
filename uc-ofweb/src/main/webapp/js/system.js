var cpu=new Array(10);
var cpuTime=new Array(10);
var memory=new Array(10);
var cpuChart = echarts.init(document.getElementById('cpuDiv'));
var memoryChart = echarts.init(document.getElementById('memoryDiv'));
var cpuGaugeChart = echarts.init(document.getElementById('cpuGaugeDiv'));
var memoryGaugeChart = echarts.init(document.getElementById('memoryGaugeDiv'));
function systemInit(){
    drawCpuEChart();
    drawMemoryEChart();
    drawCpuGauge();
    drawMemoryGauge();
    timeTicket = setInterval(function () {
        getData();
    }, 2000);
}

function drawCpuEChart(){
    option = {
        title: {
            text: 'CPU使用率'
        },
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        dataZoom : {
            show : false,
            start : 0,
            end : 100
        },
        xAxis: {
            type : 'category',
            boundaryGap : true,
            data : cpuTime
        },
        yAxis: {
            type: 'value',
            scale: true,
            boundaryGap: [0.2, 0.2],
        },
        series: [{
            name: 'cpu 使用率',
            type: 'line',
            showSymbol: true,
            hoverAnimation: false,
            detail: {formatter:'{value}%'},
            data:cpu
        }]
    };
    cpuChart.setOption(option);
}

function drawMemoryEChart(){
    option = {
        title: {
            text: '内存使用率'
        },
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        dataZoom : {
            show : false,
            start : 0,
            end : 100
        },
        xAxis: {
            type : 'category',
            boundaryGap : true,
            data : cpuTime
        },
        yAxis: {
            type: 'value',
            scale: true,
            boundaryGap: [0.2, 0.2],
        },
        series: [{
            name: '内存使用率',
            type: 'line',
            showSymbol: true,
            hoverAnimation: false,
            detail: {formatter:'{value}%'},
            data:memory
        }]
    };
    memoryChart.setOption(option);
}
function drawCpuGauge(){
    option = {
        tooltip : {
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: 'cpu使用率',
                type: 'gauge',
                detail: {formatter:'{value}%'},
                data: [{value: 50, name: 'cpu使用率'}]
            }
        ]
    };
    cpuGaugeChart.setOption(option);
}

function drawMemoryGauge(){
    option = {
        tooltip : {
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '内存使用率',
                type: 'gauge',
                detail: {formatter:'{value}%'},
                data: [{value: 50, name: '内存使用率'}]
            }
        ]
    };
    memoryGaugeChart.setOption(option);
}

function  getData(){
    var cpuGaugeOptions = cpuGaugeChart.getOption();
    var cpuOptions = cpuChart.getOption();
    var memoryGaugeOptions = memoryGaugeChart.getOption();
    var memoryOptions = memoryChart.getOption();
    var url=$("#projectContext").val()+"/system/info";
    $.ajax({
        url: url,
        cache: false,
        dataType: "json",
        type: "get",
        async: false,
        error: function (xmlHttpRequest, error) {
            cpuChart.showLoading();
            memoryChart.showLoading();
        },
        success: function (result) {
            if(result.ret>=0 && result.data){
                temp=result.data;
                var cpuRate=String(Math.round(temp.cpuRate*100*100)/100);
                var memoryRate = String(Math.round(temp.memoryRate*100)/100);
                if(cpu.length>=10){
                    cpu.shift();
                    cpu.push({name:"使用率：",value:cpuRate});
                }else {
                    cpu.push(cpuRate);
                }
                if(memory.length>=10){
                    memory.shift();
                    memory.push({name:"使用率：",value:memoryRate});
                }else {
                    memory.push(memoryRate);
                }
                if(cpuTime.length>=10){
                    cpuTime.shift();
                    cpuTime.push(new Date(temp.time).toLocaleTimeString().replace(/^\D*/,''));
                }else {
                    cpuTime.push(new Date(temp.time).toLocaleTimeString().replace(/^\D*/,''));
                }
                cpuOptions.series[0].data=cpu;
                cpuOptions.xAxis[0].data=cpuTime;
                memoryOptions.series[0].data=memory;
                memoryOptions.xAxis[0].data=cpuTime;
                cpuGaugeOptions.series[0].data[0].value=cpuRate;
                memoryGaugeOptions.series[0].data[0].value = memoryRate;
                cpuChart.setOption(cpuOptions);
                memoryChart.setOption(memoryOptions);
                cpuGaugeChart.setOption(cpuGaugeOptions);
                memoryGaugeChart.setOption(memoryGaugeOptions);
                $("#totalMemory").val(Math.round((temp.totalMemory)/(1024*1024)*100)/100+"M");
                $("#usedMemory").val(Math.round((temp.usedMemory)/(1024*1024)*100)/100+"M");
                $("#jvmTotalMemory").val(Math.round((temp.jvmTotalMemory)/(1024*1024)*100)/100+"M");
                $("#jvmUsedMemory").val(Math.round((temp.jvmTotalMemory-temp.jvmFreeMemory)/(1024*1024)*100)/100+"M");
                $("#cpuMhz").val(temp.cpuMhz+"Mhz");
                $("#cpuVender").val(temp.cpuVender+"/"+temp.cpuModel);
            }
        }
    });
}
