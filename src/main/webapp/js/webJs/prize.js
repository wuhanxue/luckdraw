/**
 * 奖品脚本文件
 *
 * */

/**
 * 页面加载
 */
function loadPrizeLIst() {
    $('#newPanel').hide();//默认新增编辑面板隐藏
}

/**
 * 新增按钮
 */
function addData() {
    //删除克隆行
    $('#clone').siblings().not($('#plusBtn')).remove();
    $('#newPanel').find("H3").text('奖品添加')//添加标题
   $('#newPanel').show(1000);//新增编辑面板显示
}

/**
 * 关闭按钮
 */
function closed() {
    $('#newPanel').hide(1000)//新增编辑面板隐藏
}

/**
 * 克隆行
 * @param item
 */
function addNewLine(item) {
    var tr=$(item).parent().parent().prev();
    var cloneTr=tr.clone();
    cloneTr.show();
    cloneTr.insertAfter(tr);
    cloneTr.removeAttr('id')
    cloneTr.children().find("input").val("");
    cloneTr.children().find("input:checkbox").prop('checked', false);
      cloneTr.children("td:eq(0)").find("a").remove();
    var delBtn = "<a class='btn btn-default btn-xs' onclick='delLine(this);'><span class='glyphicon glyphicon-minus' aria-hidden='true'></span></a>&nbsp;";
    cloneTr.children("td:eq(0)").prepend(delBtn);

}

/**
 * 删除行
 * @param e
 */
function delLine(e) {
    var tr = e.parentElement.parentElement;
    tr.parentNode.removeChild(tr);


}

/**
 * 保存方法
 */
function save() {
    $('.myclass').each(function () {
        var mode;
        if($(this).children('td').eq(4).find('label').children('input').eq(0).prop('checked')==true){
            mode=0  // 按桌抽取
        }
        if($(this).children('td').eq(4).find('label').children('input').eq(1).prop('checked')==true){
            mode=1   // 随机抽取
        }
        if($(this).children('td').eq(4).find('label').children('input').eq(2).prop('checked')==true){
            mode=2  //  桌位抽取
        }
        // if ($(this).children('td').eq(2).find('input').prop('type') != 'text') {
        //     var formFile = new FormData();
        //     var imageFile = $(this).children('td').eq(2).find("input[name='file']")[0].files[0];
        //     formFile.append("imageFile",imageFile);
        // }
        var data={
            level:$(this).children('td').eq(0).find('input').val(),
            name:$(this).children('td').eq(1).find('input').val(),
            allNumber:$(this).children('td').eq(2).find('input').val(),
            number:$(this).children('td').eq(2).find('input').val(),
            mode:mode,
        }
        console.log(data);
        $.ajax({
            type: "POST",                       // 方法类型
            url: "prize", // url
            data:JSON.stringify(data),
            async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result != undefined && result.status == "success"){
                    alert(result.message);
                    window.location.href="drawSetting"
                }
            },
            error: function (result) {
            alert("服务器异常!")
            }
        });
        //添加图片路径
        var imageFile = $(this).children('td').eq(3).find("input[name='file']")[0].files[0];
        console.log("文件:"+imageFile)
        if(imageFile!=undefined){
            $(this).children('td').eq(3).find('form').submit();
        }

    })

}


/**
 * 删除方法
 * @param item
 */
function cutOff(item) {
if(confirm("确认删除?")){
    var id=$(item).parent().prev().html();
    console.log(id);

    $.ajax({
        type: "DELETE",                       // 方法类型
        url: "prize", // url
        data:{"id":id},
        async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
        dataType: "json",
        // contentType: 'application/json;charset=utf-8',
        success: function (result) {
            if (result != undefined && result.status == "success"){
                alert(result.message);
                window.location.reload()
            }
        },
        error: function (result) {
            alert("服务器异常!")
        }
    })
}
}


/**
 * 修改跳转页面
 * @param item
 */
function adjust(item) {

    $('#adjust').show()
    var id=$(item).parent().prev().html();
  console.log(id)
    $.ajax({
        type: "PUT",                       // 方法类型
        url: "prize", // url
        data:{"id":id},
        async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
        dataType: "json",
        // contentType: 'application/json;charset=utf-8',
        success:function (result) {
            if (result != undefined && result.status == "success"){
                console.log(result)
            }
        },
        error:function (result) {

        }
    })


}


//修改页面的修改图片
function adjustPic() {
       $('#img').hide();//图片隐藏


      $('#adjustForm').removeAttr('class');//上传form显示

    $('#adjustButton').hide();   //修改图片按钮隐藏

    $('#cancelButton').removeAttr('class');//取消修改按钮显示





}

//取消修改
function cancelPic() {
    //1图片显示
    $('#img').show();//图片显示

    //2上传表单隐藏
    $('#adjustForm').attr('class','hide');//上传form隐藏

    //3修改图片按钮显示
    $('#adjustButton').show();


    //5取消修改按钮隐藏

    $('#cancelButton').attr('class','hide');//取消修改按钮隐藏
}

//修改方法实现
function adjustConfirm() {

    $('.myclass').each(function () {
        var mode;
        if($(this).children('td').eq(4).find('label').children('input').eq(0).prop('checked')==true){
            mode=0;
        }
        if($(this).children('td').eq(4).find('label').children('input').eq(1).prop('checked')==true){
            mode=1;
        }
        if($(this).children('td').eq(4).find('label').children('input').eq(2).prop('checked')==true){
            mode=2;
        }
        var data={
            level:$(this).children('td').eq(0).find('input').val(),
            id:$(this).children('td').eq(5).html(),
            name:$(this).children('td').eq(1).find('input').val(),
            allNumber:$(this).children('td').eq(2).find('input').val(),
            number:$(this).children('td').eq(2).find('input').val(),
            mode:mode,
            imgUrl:$(this).children('td').eq(6).html(),
        };
        console.log(data);
        $.ajax({
            type: "PUT",                       // 方法类型
            url: "prize", // url
            data:JSON.stringify(data),
            async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result != undefined && result.status == "success"){
                    alert(result.message)
                    window.location.href="drawSetting"
                }
            },
            error: function (result) {
                alert("服务器异常!")
            }
        });
        //添加图片路径
        var imageFile = $(this).children('td').eq(3).find("input[name='file']")[0].files[0];
        console.log(imageFile)
        if(imageFile!=undefined){
            $(this).children('td').eq(3).find('form').submit();
        }
    })
}

