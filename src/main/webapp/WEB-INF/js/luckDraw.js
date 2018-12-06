var g_Interval = 50;     // 数字跳转延迟速度(毫秒)
var g_Timer;           // 延迟执行代码块
var running = false; // 是否抽奖
var list = [[1, 2], [2, 1], [1, 3], [1, 4], [1, 1], [2, 2]];  // 参与抽奖的桌号和座位号
var winnerNumber = 3;  // 设置中奖人数
var listWinner = [];  // 中奖的人的桌号和座位号数据

function beginRndNum(trigger) {
    if (running) {
        running = false;
        clearTimeout(g_Timer); //取消延时
        updateRndNum();  // 设置最终的中奖号码(保证不存在重复)
        $(trigger).text("开始");
        $("span[id^='tableId']").css('color', 'red');
        $("span[id^='locationId']").css('color', 'red');
    } else {
        running = true;
        $("span[id^='tableId']").css('color', 'black');
        $("span[id^='locationId']").css('color', 'black');
        $(trigger).text("停止");
        listWinner = [];   // 中奖人清空
        beginTimer();
    }
}

/**
 * 获取抽奖编号并显示
 */
function updateRndNum() {
    for (var i = 0; i < winnerNumber; i++) {
        var $i = i;
        var num = Math.floor(Math.random() * list.length);  // 序号
        //var num1 = Math.floor(Math.random() * list.length + 1);  // 座位号
        $("#tableId" + $i).html(list[num][0]);
        $("#locationId" + $i).html(list[num][1]);
        if (!running) { // 停止随机后
            if (listWinner.indexOf(list[num]) > -1) { //包含该元素
                var j = 0;
                while (listWinner.indexOf(list[j]) > -1) { // 执行到不包含该元素为止
                    j++;  // 寻找不包含的元素
                }
                $("#tableId" + $i).html(list[j][0]);
                $("#locationId" + $i).html(list[j][1]);
                listWinner.push(list[j]);
            } else { // 不包含该元素
                listWinner.push(list[num]);
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
 * 保存中奖数据并跳转到中奖名单
 */
function saveWinner() {
    for (var i = 0; i < winnerNumber; i++) {
        var $i = i;
        var seat = {};
        seat.tableId = parseInt($("#tableId" + $i).text());
        seat.locationId = parseInt($("#locationId" + $i).text());
        console.log(seat);
        $.ajax({
            type: "put",
            url: "luckDraw",
            data: JSON.stringify(seat),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {

            }
        });
    }
}