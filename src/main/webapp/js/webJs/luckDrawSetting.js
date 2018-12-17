var list = [];
/**
 * 页面初始化设置
 * */
function loadPrize() {
    $.ajax({   // 获取奖品信息
        type: "POST",
        url: "luckDrawSetting1",
        dataType: "json",
        success:function(result){
            console.log(result);
            if(result != null && result.status === "success"){
                list = result.data;
                for(var i in list){ //设置奖品下拉框
                    if(list[i].number > 0){  // 奖品数小于零不显示
                        var option = "<option value='"+list[i].id+"'>"+list[i].level+":"+list[i].name+"</option>";
                        $("#prizes").append(option);  // 插入
                    }
                }
                $("#prizes").get(0).selectedIndex = -1;  //默认选中空白框
            }
        },
        error:function(){
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
    for(var i = 0; i < list.length; i++){
        if (list[i].id === id) {
            console.log(list[i]);
            console.log(list[i].level);
            number = list[i].number;  //剩余奖品数
            $("#level").html(list[i].level);         // 设置奖品等级
            $("#number").html(number);   // 设置奖品剩余名额
            $("#img").attr("src", "../../image/"+list[i].imgUrl);  // 设置奖品图片
            localStorage.prizeLevel = list[i].level;  // 奖品等级
            localStorage.prizeName = list[i].name;    // 奖品名字
            localStorage.prizeId = id;             // 奖品编号
            localStorage.prizeMode = list[i].mode;   // 抽奖方式：true:随机抽取；false:按桌抽取
        }
    }
    if(localStorage.prizeMode === "true"){ // 随机抽奖
        $("#max").show();
      //  $("#table").hide();
        $("#everyTable").hide();
        $("#numberMAX").children().remove(); //删除旧数据
        for (var i = 1; i < number + 1; i++) { // 根据奖品数插入抽奖人数下拉框
            var option = "<option value='" + i + "'>" + i + "</option>";
            $("#numberMAX").append(option);
        }
        localStorage.winnerNumber = parseInt($("#numberMAX").find("option:selected").val());  // 将抽奖人数传递到下一页面
    }else{
        $("#max").hide();
      //  $("#table").show();
        $("#everyTable").show();
      //  localStorage.tableNumber = parseInt($("#tableNumber").find("option:selected").val());
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
function setTableNumber(item){
    localStorage.tableNumber = parseInt($(item).find("option:selected").val());
}

/**
 * 设置按桌抽奖的每桌抽取人数
 * @param item
 */
function setEveryTableNumber(item){
    localStorage.everyTableNumber = parseInt($(item).find("option:selected").val());
}