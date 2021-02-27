$(document).ready(function () {
    getConfig();
});

var mConfig;

function getConfig() {
    $.ajax({
        url: "api/config", success: function (result) {
            result = JSON.parse(result);
            mConfig = result;
            initNavList();
        }
    });

}

/**
 * 初始化导航栏，遍历config中categoryList参数
 */
function initNavList() {
    let navList = mConfig.categoryList;

    $("#hdt-nav").empty();
    for (var index = 0; index < navList.length; index++) {
        let itemName = navList[index].name;
        let itemId = index;
        $("#hdt-nav").append("<li id=nav_item_" + itemId + " role='presentation' onclick='showContent(\"" + index + "\")'><a href='#'>" + itemName + "</a></li>");
    }

    $(document).on("click", "#hdt-nav li", function () {
        $("#hdt-nav li").each(function () {
            $(this).removeClass('active');
        });
        $(this).addClass('active');
    });

    $('#nav_item_0').trigger('click');
}

/**
 * 导航栏item点击后，在内容区显示对应内容
 *
 * @param index 导航栏在列表中所处的index
 */
function showContent(navIndex) {
    let inputList = mConfig.categoryList[navIndex].inputList

    $("#hdt-content").empty();
    for (var index = 0; index < inputList.length; index++) {
        let inputItem = inputList[index]
        let inputDemos = inputItem.demos
        if (inputItem.desc != null) {
            // 添加输入框的文案描述
            if (index === 0) {
                $("#hdt-content").append("<div><p>" + inputItem.desc + "</p></div>");
            } else {
                $("#hdt-content").append("<div style='margin-top: 20px'><p>" + inputItem.desc + "</p></div>");
            }
        }

        // 添加demo按钮
        if (inputDemos != null && inputDemos.length > 0) {
            // 添加demo的div
            let demoDivId = "hdt-demo-" + index;
            $("#hdt-content").append("<div id=" + demoDivId + " style='margin-bottom: 16px'></div>");
            $("#" + demoDivId).append("<span>示例：</span>")

            for (var demoIndex = 0; demoIndex < inputDemos.length; demoIndex++) {
                // 添加demo的button
                let demo = inputDemos[demoIndex];
                var mDemoDiv = document.getElementById(demoDivId)
                var bt = document.createElement("button")
                bt.style.marginRight = '8px';
                bt.innerHTML = demo.name;
                bt.className = "btn btn-default";
                bt.onclick = function () {
                    if (demo.type === 1) {
                        // 该数据需要请求path
                        let path = demo.data
                        $.ajax({
                            url: path,
                            type: 'GET',
                            dataType: 'text',
                            success: function (result) {
                                console.log(result)
                                $("#hdt-textarea-" + inputItem.paramKey).text(result);
                            }
                        });
                    } else if (demo.type === 2) {
                        // 直接显示该数据
                        $("#hdt-textarea-" + inputItem.paramKey).text(demo.data);
                    }
                }
                mDemoDiv.appendChild(bt)
            }
        }

        // 添加选择框
        $("#hdt-content").append("<div><textarea id=hdt-textarea-" + inputItem.paramKey + " style='width: 100%;height: 200px;' placeholder='Write something...'></textarea></div>");

    }

    // 发送按钮
    $("#hdt-content").append("<div><button class=\"btn btn-info\"  onclick='send(" + navIndex + ")'>发送</button></div>");
}

/**
 * 获取demo数据
 *
 * @param type 1是文件
 * @param data 2是文本
 */
function fetchDemo(type, data) {
    if (type === 1) {
        let path = data
        $.ajax({
            url: path, success: function (result) {
                console.log(result.toString())
                result = JSON.parse(result);
            }
        });
    } else if (type === 2) {
        console.log(data)
    }
}

/**
 */
function send(navIndex) {
    let inputList = mConfig.categoryList[navIndex].inputList
    let categoryId = mConfig.categoryList[navIndex].id
    var postData = {};
    postData.categoryId = categoryId;
    for (var index = 0; index < inputList.length; index++) {
        let inputItem = inputList[index]
        let inputText = $("#hdt-textarea-" + inputItem.paramKey).val()
        postData[inputItem.paramKey] = inputText;
    }
    console.log("上传数据：")
    console.log(postData)

    $.ajax({
        url: "api/submit",
        type: 'POST',
        data: postData,
        success: function (response) {
            let text = response.toString()
            showSuccessInfo(response)
        }
    })

}

function showSuccessInfo(message) {
    var snackbarId = "snackbar";
    var snackbarElement = $("#" + snackbarId);
    snackbarElement.addClass("show");
    snackbarElement.css({"backgroundColor": "#5cb85c"});
    snackbarElement.html(message)
    setTimeout(function () {
        snackbarElement.removeClass("show");
    }, 3000);
}

function showErrorInfo(message) {
    var snackbarId = "snackbar";
    var snackbarElement = $("#" + snackbarId);
    snackbarElement.addClass("show");
    snackbarElement.css({"backgroundColor": "#d9534f"});
    snackbarElement.html(message)
    setTimeout(function () {
        snackbarElement.removeClass("show");
    }, 3000);
}

function getHashValue(key) {
    var matches = location.hash.match(new RegExp(key + '=([^&]*)'));
    return matches ? matches[1] : null;
}
