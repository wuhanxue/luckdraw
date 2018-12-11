/**
 * 缺省载入函数
 */
function loadSeatList() {
    // 默认新增编辑面板隐藏
    $('#newPanel').hide();
}

/**
 * 增加数据
 */
function addData() {
    // 删除克隆行
    $('#clone').siblings().not($('#plusBtn')).remove();
    // 添加标题
    $('#newPanel').find("H3").text('座位添加');
    // 新增编辑面板显示
    $('#newPanel').show(1000);
}

/**
 * 关闭新增面板
 */
function closed() {
    // 新增编辑面板隐藏
    $('#newPanel').hide(1000);
}

/**
 * 新增
 * @param item
 */
function addNewLine(item) {
    var tr=$(item).parent().parent().prev();
    var cloneTr=tr.clone();
    cloneTr.show();
    cloneTr.insertAfter(tr);
    cloneTr.removeAttr('id');
    cloneTr.children().find("input").val("");
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
 * 保存座位数据
 */
function save() {
    $('.myclass').each(function () {
        var data = {
            tableId: $(this).children('td').eq(0).find('input').val(),
            locationId: $(this).children('td').eq(1).find('input').val(),
            department: $(this).children('td').eq(2).find('input').val(),
            name: $(this).children('td').eq(3).find('input').val()
        };
        console.log(data);
        $.ajax({
            type: "POST",                       // 方法类型
            url: "seat", // url
            data: JSON.stringify(data),
            async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result != undefined && result.status == "success") {
                    alert(result.message);
                    window.location.reload();
                }
            },
            error: function (result) {
                alert("服务器异常!");
            }
        });
    });
}

/**
 * 删除条目
 */
function deleteItem(item) {
    if(confirm("确认删除?")) {
        var id = $(item).parent().prev().html();
        console.log(id);
        $.ajax({
            type: "DELETE",                       // 方法类型
            url: "seat", // url
            data: {
                "id":id
            },
            async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
            dataType: "json",
            // contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result != undefined && result.status == "success") {
                    alert(result.message);
                    window.location.reload();
                }
            },
            error: function (result) {
                alert("服务器异常!")
            }
        })
    }
}

/**
 * 确认修改座位数据
 */
function adjustConfirm() {
    $('.myclass').each(function () {
        var data={
            tableId: $(this).children('td').eq(0).find('input').val(),
            locationId: $(this).children('td').eq(1).find('input').val(),
            department: $(this).children('td').eq(2).find('input').val(),
            name: $(this).children('td').eq(3).find('input').val(),
            id: $(this).children('td').eq(4).text()
        };
        $.ajax({
            type: "PUT",                       // 方法类型
            url: "seat", // url
            data: JSON.stringify(data),
            async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result != undefined && result.status == "success") {
                    alert(result.message);
                    window.location.href = "/seat";
                }
            },
            error: function (result) {
                alert("服务器异常!")
            }
        });
    });
}

