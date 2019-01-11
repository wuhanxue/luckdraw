var g_Interval = 20;     // 数字跳转延迟速度(毫秒)
var g_Timer;           // 延迟执行代码块
var t_Interval = 60;  // 桌位抽奖奖品跳转延迟速度
var t_Timer;           // 延迟执行代码块
var running = false; // 是否抽奖
var runningTable = false;
var list = [[1, 2], [2, 1], [1, 3], [1, 4], [1, 1], [2, 2]];  // 参与抽奖的桌号和座位号
var winnerNumber = 1;  // 设置中奖人数
var add = false;   // 新增是否成功
var tableList = [];  // 存放桌号
var everyTableNum = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];   // 每桌座位号
var isEnd = 1;  // 用于计数：桌位抽奖是否结束,为偶数时抽取桌号，为奇数时抽取座位号
var winTableList = [];  // 中奖桌号
var listWinner = [];  // 中奖的人的桌号和座位号数据
var tableNumber = 0;   // 按桌抽奖每次抽取桌数
var prizeList = [];    // 桌位抽奖奖品
var randomPrizeNumberList = [];   // 桌位抽奖随机数组
var tableNum = 0;           // 随机桌号
/**
 * 空格键按下事件
 */
$(document).keydown(function (event) {
    if (event.keyCode === 32) {  // 空格键抽奖
        if ($("#begin").get(0).style.display !== "none") {   // 如果开始按钮显示时则空格键为抽奖开始或暂停，如果开始键隐藏后空格键为保存键
            beginRndNum();
        } else {
            save();
        }
    }
    // if(event.keyCode === 13) {  // 回车保存
    //     save();
    // }
});

/**
 * 抽奖事件
 */
function beginRndNum() {
    if (running) {  // 停止抽奖
        isEnd++;
        running = false;
        clearTimeout(g_Timer); //取消延时
        $("#begin").text("开始");
        $("span[id^='tableId']").css('color', 'red');
        $("span[id^='locationId']").css('color', 'red');
        updateRndNum();  // 设置最终的中奖号码(保证不存在重复)
        console.log(JSON.parse(localStorage.getItem('winTableList')));
        if (localStorage.prizeMode === "2") {  // 桌位抽奖需要分为两次抽取
            if (isEnd % 2 === 1) {
                $("#begin").hide();
                $("#list").show();  // 按钮显示
            } else {  // 抽桌
                $("#begin").show(); // 按钮显示
                $("#list").hide();
            }
        } else {
            $("#begin").hide();
            $("#list").show();  // 按钮显示
        }
        // setTimeout(function () {     // 不点击名单3秒后自动跳转
        //     save();
        // }, 3000); // 抽奖结束不点击三秒后自动执行
    } else {   // 开始抽奖
        running = true;
        $("span[id^='tableId']").css('color', 'black');
        $("span[id^='locationId']").css('color', 'black');
        //$("#begin").show();
        $("#begin").text("停止");
        beginTimer();
        $("#list").hide();  // 按钮隐藏
        listWinner = [];   // 中奖人清空
    }
}

/**
 * 获取抽奖编号并显示
 */
function updateRndNum() {
    if (localStorage.prizeMode === "1") {  // 随机抽奖
        for (var i = 0; i < winnerNumber; i++) {
            var $i = i;
            var num = Math.floor(Math.random() * list.length);  // 序号
            if (tableList.length > 0)
                while ($.inArray(list[num][0], JSON.parse(localStorage.getItem('winTableList'))) > 0 || $.inArray(list[num][0], tableList) === -1) {  // 该桌号本次抽奖已经获奖过了或者不在未中奖桌号中则不能再获奖
                    num = Math.floor(Math.random() * list.length);
                }
            $("#tableId" + $i).html(list[num][0]);
            $("#locationId" + $i).html(list[num][1]);
            if (!running) { // 停止随机后
                listWinner.push(list[num]);
                var tList = JSON.parse(localStorage.getItem('winTableList'));
                tList.push(list[num][0]);
                localStorage.setItem('winTableList', JSON.stringify(tList));
                console.log("tableList：");
                console.log(tableList);
                for (var j = 0; j < tableList.length; j++) { // 将获奖桌号从未中奖桌号中删除
                    if (tableList[j] === list[num][0]) {
                        tableList.splice(j, 1);
                    }
                }
                console.log(tableList);
                console.log("list：");
                console.log(list);
                list.splice(num, 1);   // 将中奖者从数组中删除
                console.log(list);

            }
        }
    } else if (localStorage.prizeMode === "2") {  // 桌位抽奖
        for (var i = 0; i < winnerNumber; i++) {
            var $i = i;
            if (running) {
                if (isEnd % 2 === 1) { // 先抽桌号
                    tableNum = Math.floor(Math.random() * tableList.length);  // 桌号序号
                    if (tableList.length > 0)
                        while ($.inArray(tableList[tableNum], JSON.parse(localStorage.getItem('winTableList'))) > 0 || $.inArray(tableList[tableNum], tableList) === -1) {  // 该桌号本次抽奖已经获奖过了则不能再获奖
                            tableNum = Math.floor(Math.random() * tableList.length);
                        }
                    $("#tableId" + $i).html(tableList[tableNum]);
                } else {
                    num = Math.floor(Math.random() * list.length);  // 座位号序号
                    if (list.length > 0)
                        while (list[num][0] !== parseInt($("#tableId" + $i).text())) {  // 如果不是该桌则继续抽
                            num = Math.floor(Math.random() * list.length);  // 座位号序号
                        }
                    $("#locationId" + $i).html(list[num][1]);
                }
            }
            if (!running) {// 停止随机后
                if (isEnd % 2 === 0) {
                    var tList = JSON.parse(localStorage.getItem('winTableList'));
                    tList.push(parseInt($("#tableId" + $i).text()));
                    localStorage.setItem('winTableList', JSON.stringify(tList));
                    console.log("tableList:");
                    console.log(tableList);
                    console.log("tableNum:" + tableNum);
                    console.log("tableId:" + tableList[tableNum]);
                    tableList.splice(tableNum, 1);   // 将中奖者桌号从数组中删除
                    console.log(tableList);
                } else {// 座位号抽取结束之后
                }
            }
        }
    } else {  // 按桌抽奖
        for (var p = 0; p < localStorage.everyTableNumber; p++) { // 每桌抽取人数
            var $i = p;
            var num = Math.floor(Math.random() * everyTableNum.length);  // 序号(默认每桌10人)
            while($.inArray(everyTableNum[num], JSON.parse(localStorage.getItem('winLocationList'))) > 0) {  // 如果该座位号已经中过将则不能再中
                num = Math.floor(Math.random() * everyTableNum.length);
            }
            $("#locationId" + $i).html(everyTableNum[num]);  // 座位号
            if (!running) { // 停止随机后
                if (tableNumber > tableList.length || tableNumber === -1) {  // 如果抽取桌数大于剩余桌数或者抽取桌数为-1则设置抽取桌数为剩余桌数
                    tableNumber = tableList.length;
                }
                for (var k = 0; k < tableNumber; k++) {
                    listWinner.push([tableList[k], everyTableNum[num]]);   // 添加中奖者
                }
                everyTableNum.splice(num, 1);   // 将中奖者从数组中删除
                var lList = JSON.parse(localStorage.getItem('winLocationList'));
                lList.push(everyTableNum[num]);
                localStorage.setItem('winLocationList', JSON.stringify(lList));
            }
        }
    }
}

/**
 * 设置时间延迟
 * */
function beginTimer() {
    g_Timer = setTimeout(beat, g_Interval);
}

/**
 * 时间延迟时间，并执行数字滚动
 * */
function beat() {
    g_Timer = setTimeout(beat, g_Interval);
    updateRndNum();
}

/**
 * 设置时间延迟
 * */
function beginTimer1() {
    t_Timer = setTimeout(beat1, t_Interval);
}

/**
 * 时间延迟时间，并执行数字滚动
 * */
function beat1() {
    t_Timer = setTimeout(beat1, t_Interval);
    updateRndNum1();
}

/**
 * 下一轮或者保存数据并跳转中奖名单页面
 */
function save() {
    saveWinner();
    if(parseInt(localStorage.tableNumber) > -1){
        localStorage.tableLength = tableList.length - parseInt(localStorage.tableNumber);
    }else {   // 全部抽取
        localStorage.tableLength = tableList.length;
    }
    window.location.href = 'showWinnerList';
}

/**
 * 保存中奖数据并跳转到中奖名单
 */
function saveWinner() {
    var seatList = [];     // 初始化
    if (localStorage.prizeMode === "1" || localStorage.prizeMode === "2") { // 随机抽取/桌位抽取
        for (var i = 0; i < winnerNumber; i++) {
            var $i = i;
            var seat = {};
            seat.tableId = parseInt($("#tableId" + $i).text());
            seat.locationId = parseInt($("#locationId" + $i).text());
            var winners = {};
            winners.prizeId = localStorage.prizeId; // 保存奖品ID
            winners.number = localStorage.winnerDrawNumber;
            var prize = {};
            prize.number = localStorage.winnerNumber; // 奖品数
            winners.prize = prize;
            seat.winners = winners;
            seatList.push(seat);
        }
    } else {        // 按桌抽取
        for (var i = 0; i < listWinner.length; i++) {
            var seat = {};
            seat.tableId = listWinner[i][0];
            seat.locationId = listWinner[i][1];
            var winners = {};
            winners.prizeId = localStorage.prizeId; // 保存奖品ID
            winners.number = localStorage.winnerDrawNumber;
            var prize = {};
            prize.number = localStorage.winnerNumber; // 奖品数
            winners.prize = prize;
            seat.winners = winners;
            seatList.push(seat);
        }
    }
    //localStorage.tableNumber
    $.ajax({   // 将中奖者信息更新到数据库
        type: "POST",
        url: "updateWinner",
        async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
        data: {
            "seats": JSON.stringify(seatList)
        },
        dataType: "json",
        success: function () {
            console.log("成功更新");
        }
    });
}

/**
 * 关闭面板
 */
function closed() {
    // 新增编辑面板赢藏
    $('#newPanel').hide(1000);
}


/**
 * 随机获取按桌抽奖奖品
 */
function randomSetModeTwoPrize() {
    $.ajax({   // 获取桌位抽奖的且剩余量大于0的奖品数组
        type: "POST",
        url: "getPrizeListByMode",
        async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
        data: {
            mode: 2
        },
        dataType: "json",
        success: function (result) {
            if (result != null && result.status === "success") {
                prizeList = result.data;
                for (var i = 0; i < prizeList.length; i++) {
                    for (var j = 0; j < prizeList[i].number; j++) {
                        randomPrizeNumberList.push(i);
                    }
                }
                // console.log(prizeList);
                // console.log(randomPrizeNumberList);
            }
        }
    });
}

/**
 * 随机设置桌位抽奖奖品
 */
function updateRndNum1() {
    var num = Math.floor(Math.random() * randomPrizeNumberList.length);  // 序号()
    $("#prize").text(prizeList[randomPrizeNumberList[num]].level + "：" + prizeList[randomPrizeNumberList[num]].name); // 设置奖品等级和名称
    if (!runningTable) {  // 停止抽奖后
        localStorage.prizeLevel = prizeList[randomPrizeNumberList[num]].level;  // 奖品等级
        localStorage.prizeName = prizeList[randomPrizeNumberList[num]].name;    // 奖品名字
        localStorage.prizeId = prizeList[randomPrizeNumberList[num]].id;         // 奖品编号
    }
}

/**
 * 开始抽取随机奖品
 */
function beginRndNum1() {
    if (runningTable) {  // 停止抽奖
        runningTable = false;
        clearTimeout(t_Timer); //取消延时
        updateRndNum1();  // 设置最终的中奖号码(保证不存在重复)
    } else {   // 开始抽奖
        runningTable = true;
        beginTimer1();
    }
}