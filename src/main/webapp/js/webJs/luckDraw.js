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

function beginTimer() {
    g_Timer = setTimeout(beat, g_Interval);
}

function beat() {
    g_Timer = setTimeout(beat, g_Interval);
    updateRndNum();
}

/**
 * 根据抽奖人数加载抽奖框
 */
function loadNumber() {
    // 获取所有参与抽奖的员工数据
    var url = "luckDraw";
    // $.ajax({
    //     type:"GET",
    //     url: url,
    //     dataType:"json",
    //     success: function(result){
    //        console.log("data");
    //        console.log(result);
    //     }
    // });
    $("#class1").nextAll().remove();  // 删除旧数据
    for (var i = 0; i < winnerNumber; i++) {
        if (i % 2 === 0) {
            var tr = "<tr>\n" +
                "</tr>";
            $("#table").append(tr);
        }
        var td = "<td class=\"text-center\">\n" +
            "            <span>桌号：</span>&nbsp<span title=\"\" id='tableId" + i + "' style='width: 50px'>--</span>\n" +
            "            <span>座号：</span>&nbsp<span title=\"\" id='locationId" + i + "' style='width: 50px'>--</span>\n" +
            " </td>";
        $("#table").find("tr:last").append(td);   // 将td 插入到最新的tr中
    }

}