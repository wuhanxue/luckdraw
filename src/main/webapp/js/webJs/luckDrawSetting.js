var list = [];   // 承装奖品

/**
 * 页面初始化设置
 * */
function loadPrize() {
    var num = 0;
    localStorage.setItem('winTableList', JSON.stringify([]));   // 初始化获奖桌号
    localStorage.setItem('winLocationList', JSON.stringify([]));   // 初始化按桌抽取中奖座位号
    localStorage.setItem('winLocationList', JSON.stringify([]));   // 初始化按桌抽取中奖座位号
    $.ajax({   // 获取奖品信息
        type: "POST",
        url: "luckDrawSetting1",
        dataType: "json",
        success: function (result) {
            console.log(result);
            if (result != null && result.status === "success") {
                list = result.data;
                for (var i in list) { //设置奖品下拉框
                    if (list[i].number > 0) {  // 奖品数小于零不显示
                        // if(list[i].mode === 2){
                        //     if(num === 0){   // 桌位抽奖的物品只显示第一个
                        //         var option = "<option value='"+list[i].id+"' class='text-center'>"+list[i].level+"</option>";
                        //         $("#prizes").append(option);  // 插入
                        //         num++;
                        //     }
                        // }else{
                        var option = "<option value='" + list[i].id + "' class='text-center'>" + list[i].level + "</option>";
                        $("#prizes").append(option);  // 插入
                        //   }
                    }
                }
                $("#prizes").get(0).selectedIndex = -1;  //默认选中空白框
            }
        },
        error: function () {
            alert("数据获取失败！");
        }
    });
}

/**
 * 选择奖品后设置奖品图片，
 */
function setPrize(item) {
    var id = parseInt($(item).find("option:selected").val());  // 获取选中奖品的剩余数量
    var number = 0; //奖品数量
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            number = list[i].number;  //剩余奖品数
            //if(list[i].mode === 2){  // 桌位抽奖物品隐藏数字
            //    $("#level").html(list[i].name.replace(/[0-9]/ig,""));         // 设置奖品等级
            //}else {
            $("#level").html(list[i].name);         // 设置奖品等级
            //}
            $("#number").html(number);   // 设置奖品剩余名额
            $("#img").attr("src", "../../image/" + list[i].imgUrl);  // 设置奖品图片
            localStorage.prizeLevel = list[i].level;  // 奖品等级
            localStorage.prizeName = list[i].name;    // 奖品名字
            localStorage.prizeId = id;             // 奖品编号
            localStorage.prizeMode = list[i].mode;   // 抽奖方式：1:随机抽取；0:按桌抽取;2:桌位抽取
            if(number < 5){   // 如果剩余奖品数小于5则设置最大抽奖数为剩余奖品数
                $("#numberMAX").children().remove(); //删除旧数据
                for(var i1 = 1; i1 < number + 1; i1++){
                    var option = "<option value='" + i1 + "'>" + i1 + "</option>";
                    $("#numberMAX").append(option);
                }
            }else {
                $("#numberMAX").children().remove(); //删除旧数据
                for(var i1 = 1; i1 < 6; i1++){
                    var option = "<option value='" + i1 + "'>" + i1 + "</option>";
                    $("#numberMAX").append(option);
                }
            }
        }
    }
    if (localStorage.prizeMode === 1 || localStorage.prizeMode === 2 || localStorage.prizeMode === "1" || localStorage.prizeMode === "2") { // 随机抽奖,桌位抽奖
        $("#max").show();
        $("#everyTable").hide();
        $("#table").hide();
        if (localStorage.prizeMode === "2") {   // 桌位抽奖一次只能抽一个
            $("#numberMAX").children().remove(); //删除旧数据
            var option = "<option value='" + 1 + "'>" + 1 + "</option>";
            $("#numberMAX").append(option);
        }
        localStorage.winnerNumber = parseInt($("#numberMAX").find("option:selected").val());  // 将抽奖人数传递到下一页面
    } else { // 按桌抽奖
        $("#max").hide();
        $("#everyTable").show();
        $("#table").show();
        localStorage.tableNumber = parseInt($("#tableNumber").find("option:selected").val());
        localStorage.everyTableNumber = parseInt($("#everyTableNumber").find("option:selected").val());
    }
    localStorage.winnerDrawNumber = 0;  // 默认为只抽一次
}

/**
 * 设置抽奖人数并将其传递到下一页面
 * @param item
 */
function setNumber(item) {
    localStorage.winnerNumber = parseInt($(item).find("option:selected").val());  // 将抽奖人数传递到下一页面
}

/**
 * 设置按桌抽奖的每次抽取桌数
 * @param item
 */
function setTableNumber(item) {
    localStorage.tableNumber = parseInt($(item).find("option:selected").val());
}

/**
 * 设置按桌抽奖的每桌抽取人数
 * @param item
 */
function setEveryTableNumber(item) {
    localStorage.everyTableNumber = parseInt($(item).find("option:selected").val());
}

/**
 * 键盘按下事件
 */
$(document).keydown(function (event) {
    if (event.keyCode === 32) {  // 空格键抽奖
        window.location.href = 'luckDraw';
    }
    // if(event.keyCode === 13) {  // 回车保存
    //     save();
    // }
});