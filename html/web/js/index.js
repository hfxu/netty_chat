var qqFaces = ["微笑", "撇嘴", "色", "发呆", "得意", "流泪", "害羞", "闭嘴", "睡", "大哭",
    "尴尬", "发怒", "调皮", "呲牙", "惊讶", "难过", "酷", "冷汗", "抓狂", "吐",
    "偷笑", "愉快", "白眼", "傲慢", "饥饿", "困", "惊恐", "流汗", "憨笑", "悠闲",
    "奋斗", "咒骂", "疑问", "嘘", "晕", "疯了", "衰", "骷髅", "敲打", "再见",
    "擦汗", "抠鼻", "鼓掌", "糗大了", "坏笑", "左哼哼", "右哼哼", "哈欠", "鄙视", "委屈",
    "快哭了", "阴险", "亲亲", "吓", "可怜", "菜刀", "西瓜", "啤酒", "篮球", "乒乓",
    "咖啡", "饭", "猪头", "玫瑰", "凋谢", "嘴唇", "爱心", "心碎", "蛋糕", "闪电",
    "炸弹", "刀", "足球", "瓢虫", "便便", "月亮", "太阳", "礼物", "拥抱", "强",
    "弱", "握手", "胜利", "抱拳", "勾引", "拳头", "差劲", "爱你", "NO", "OK",
    "爱情", "飞吻", "跳跳", "发抖", "怄火", "转圈", "磕头", "回头", "跳绳", "投降",
    "激动", "乱舞", "献吻", "左太极", "右太极"];
$(function () {
    //$('#btn_send').show();
    var _editArea = $('#editArea');

    initFaceList();

    //显示隐藏发送按钮  
    //var _editAreaInterval;
    //_editArea.focus(function () {
    //    var _this = $(this), html;
    //    _editAreaInterval = setInterval(function () {
    //        html = _this.html();
    //        if (html.length > 0) {
    //            //$('#web_wechat_pic').hide();
    //            $('#btn_send').attr('disabled', 'disabled');
    //        } else {
    //            //$('#web_wechat_pic').show();
    //            $('#btn_send').removeAttr('disabled')
    //        }
    //    }, 200);
    //});

    //_editArea.blur(function () {
    //    clearInterval(_editAreaInterval);
    //});

    //显示隐藏表情栏  
    $('.web_wechat_face').click(function () {
        $('.box_ft_bd').toggleClass('hide');
        resetMessageArea();
    });

    //切换表情主题  
    $('.exp_hd_item').click(function () {
        var _this = $(this), i = _this.data('i');
        $('.exp_hd_item,.exp_cont').removeClass('active');
        _this.addClass('active');
        $('.exp_cont').eq(i).addClass('active');
        resetMessageArea();
    });

    //选中表情  
    $('.exp_cont a').click(function () {
        var _this = $(this);
        var html = '<img class="' + _this[0].className + '" title="' + _this.html() + '" src="img/spacer.gif">';
        _editArea.html(_editArea.html() + html);
        $('#web_wechat_pic').attr('disabled', true);
        $('#btn_send').show();
    });

    resetMessageArea();

    $('#btn_send').click(function () {
        var $text = $('#editArea');
        var text = $text.html().trim();
        console.log($text);
        if (text.length > 0) {
            var faceList = $('.box_ft_bd');
            if (!faceList.hasClass('hide')) {
                faceList.toggleClass('hide');
            }
            sendMessage(groupId, text);
            _editArea.html('');
        }
    });

    $('#imageFile').on('change', function () {
        var file = this.files[0];
        if (file) {
            var arr = [];
            arr[0] = file;
            weixin.uploadFile(arr, 'image', function (response) {
                if (response.code == 0) {
                    console.log(response);
                    var pics = response.content || [];
                    if (pics.length > 0) {
                        sendMessage(groupId, null, pics[0]);
                    } else {
                        alert('图片发送失败');
                    }
                }
            });
        }
    });
});
function resetMessageArea() {
    var $m = $('#messageList');
    $m.animate({'scrollTop': $m.prop('scrollHeight')}, 1);
}

function initFaceList() {
    var html = '';
    for (var i in qqFaces) {
        if (qqFaces.hasOwnProperty(i)) {
            var e = qqFaces[i];
            html += '<a title="' + e + '" type="qq" class="qqface qqface' + i + '">' + e + '</a>';
        }
    }
    $('#faceList').html(html);
}