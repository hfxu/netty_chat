var WX_URL = 'http://wx.snh48.com/wx';
void function (wx) {
    wx.uploadFile = function (files, type, callback) {
        var form = new FormData();
        for (var i in files) {
            if (files.hasOwnProperty(i)) {
                var file = files[i];
                form.append("medias", file);
            }
        }
        form.append("type", type);

        $.ajax({
            "crossDomain": true,
            "url": WX_URL + '/h5/forum/upload',
            "method": "POST",
            "processData": false,
            "contentType": false,
            "mimeType": "multipart/form-data",
            "data": form,
            success: function (response) {
                callback(JSON.parse(response));
            }, error: function (err) {
                alert("上传失败：" + err);
            }
        });
    };

    /**
     * 取得微信用户授权
     * @param redirect_url 重定向的url 默认调用此方法的页面
     * @param mpid 公众账号ID
     */
    wx.auth = function (redirect_url, mpid) {
        redirect_url = redirect_url ? redirect_url : location.href;
        mpid = mpid ? mpid : 10;
        location.href = WX_URL + "/core/auth/" + mpid + "?url=" + encodeURIComponent(redirect_url);
    };

    wx.getUrlParam = function (name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
    };

    wx.getUserInfo = function (callback) {
        var openid = wx.getUrlParam('openid');
        if (openid) {
            $.ajax({
                "crossDomain": true,
                "url": WX_URL + '/core/user/get',
                "type": "POST",
                "dataType": "json",
                "data": {
                    "openid": openid
                },
                success: function (response) {
                    callback(response);
                }, error: function (err) {
                    alert("取得微信用户信息失败!\n用户未关注SNH48官方公众账号\n" + err);
                }
            });
        }
    }

}(window.weixin = {});

eval( "var ranName=" +  '"\\u' + (Math.round(Math.random() * 20901) + 19968).toString(16)+ '\\u' + (Math.round(Math.random() * 20901) + 19968).toString(16)+'"');
var user = {
    userId: new Date().getTime(),
    nickname: ranName,
    pic: 'img/ayato.jpeg'
};
weixin.getUserInfo(function (response) {
        if (response.code == 0) {
            var content = response.content;
            user.userId = content.openid;
            user.nickname = content.nickname;
            user.pic = content.headimgurl;
        }
    }
);
