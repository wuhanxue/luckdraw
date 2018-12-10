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
                    var option = "<option value='"+list[i].id+"'>"+list[i].level+":"+list[i].name+"</option>";
                    $("#prizes").append(option);  // 插入
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
            number = list[i].number;  //剩余奖品数
            $("#level").text(list[i].name);         // 设置奖品等级
            $("#img").attr("src", "../../image/"+list[i].imgUrl);  // 设置奖品图片
            localStorage.prizeLevel = list[i].level;  // 奖品等级
            localStorage.prizeName = list[i].name;    // 奖品名字
            localStorage.prizeId = id;             // 奖品编号
        }
    }
    $("#numberMAX").children().remove(); //删除旧数据
    for (var i = 1; i < number + 1; i++) { // 根据奖品数插入抽奖人数下拉框
        var option = "<option value='" + i + "'>" + i + "</option>";
        $("#numberMAX").append(option);
    }
    $("#number").text(number);   // 设置奖品剩余名额
    localStorage.winnerNumber = parseInt($("#numberMAX").find("option:selected").val());  // 将抽奖人数传递到下一页面
}

/**
 * 设置抽奖人数并将其传递到下一页面
 * @param item
 */
function setNumber(item) {
    localStorage.winnerNumber = parseInt($(item).find("option:selected").val());  // 将抽奖人数传递到下一页面
}