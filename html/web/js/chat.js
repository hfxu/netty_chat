/**
 * 初始化ProtoBuf
 */
if (typeof dcodeIO === 'undefined' || !dcodeIO.ProtoBuf) {
    throw(new Error("ProtoBuf.js is not present. Please see www/index.html for manual setup instructions."));
}
// Initialize ProtoBuf.js
var ProtoBuf = dcodeIO.ProtoBuf;
var ChatMessage = ProtoBuf.loadProtoFile("./proto/chatmessage.proto").build("ChatMessage");
void function (c) {
    /**
     * 加入群组
     * @param groupId 群组ID
     */
    c.joinGroup = function (groupId) {
        var m = new ChatMessage();
        m.setAction(2);
        var uuid = 'U-' + user.userId + '-' + groupId + '-' + c.UUID();
        m.setUuid(uuid);
        m.setUserId(user.userId + '');
        m.setGroupId(groupId + '');
        m.setNickname(user.nickname);
        m.setPic(user.pic);
        m.setContent(user.nickname + ' 加入了群组');
        return m;
    };

    /**
     * 退出群组
     * @param groupId 群组ID
     */
    c.leaveGroup = function (groupId) {
        var m = new ChatMessage();
        m.setAction(3);
        var uuid = 'U-' + user.userId + '-' + groupId + '-' + c.UUID();
        m.setUuid(uuid);
        m.setUserId(user.userId + '');
        m.setGroupId(groupId + '');
        m.setNickname(user.nickname);
        m.setPic(user.pic);
        m.setContent(user.nickname + ' 退出了群组');
        return m;
    };

    /**
     * 创建聊天消息
     * @param groupId 群组ID
     * @param content 文本内容
     * @param pictures 图片
     * @param audios 音频
     * @param videos 视频
     * @param reply 回复
     */
    c.createMsg = function (groupId, content, pictures, audios, videos, reply) {
        var m = new ChatMessage();
        m.setAction(0);
        var uuid = 'U-' + user.userId + '-' + groupId + '-' + c.UUID();
        m.setUuid(uuid);
        m.setUserId(user.userId + '');
        m.setGroupId(groupId + '');
        m.setNickname(user.nickname);
        m.setPic(user.pic);
        if (content)m.setContent(content);
        if (pictures)m.setPictures(pictures);
        if (audios)m.setAudios(audios);
        if (videos)m.setVideos(videos);
        if (reply)m.setReply(reply);
        console.log(m);
        return m;
    };

    /**
     * 生成随机数
     * @return {string}
     */
    c.UUID = function () {
        if (window.performance && typeof window.performance.now === "function") {
            var d = new Date().getTime();
            d += performance.now(); //use high-precision timer if available
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = (d + Math.random() * 16) % 16 | 0;
                d = Math.floor(d / 16);
                return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
            });
        } else {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        }
    };

    c.parseTime = function (timestamp) {
        var str;
        timestamp = Number(timestamp) || 0;
        var now = new Date().getTime();
        var date = new Date(timestamp);
        if (now > timestamp) {
            if (now - timestamp <= 24 * 3600 * 1000) {
                str = c.formatHHMMSS(date);
            } else {
                str = c.formatDate(date);
            }
        } else {
            str = c.formatHHMMSS(date);
        }
        return str;
    };

    c.formatDate = function (date) {
        var year = date.getFullYear();
        var month = c.addZero(date.getMonth() + 1);
        var day = c.addZero(date.getDate());
        var hours = c.addZero(date.getHours());
        var minutes = c.addZero(date.getMinutes());
        var seconds = c.addZero(date.getSeconds());
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    };

    c.formatLocaleDatetime = function (date) {
        var year = date.getFullYear();
        var month = c.addZero(date.getMonth() + 1);
        var day = c.addZero(date.getDate());
        var hours = c.addZero(date.getHours());
        var minutes = c.addZero(date.getMinutes());
        var seconds = c.addZero(date.getSeconds());
        return year + "-" + month + "-" + day + "T" + hours + ":" + minutes + ":" + seconds;
    };

    c.formatYYYYMM = function (date) {
        var year = date.getFullYear();
        var month = c.addZero(date.getMonth() + 1);
        return year + "-" + month;
    };

    c.formatYYYYMMDD = function (date) {
        var year = date.getFullYear();
        var month = c.addZero(date.getMonth() + 1);
        var day = c.addZero(date.getDate());
        return year + "-" + month + "-" + day;
    };

    c.formatYYYYMMDD_zhCN = function (date) {
        var year = date.getFullYear();
        var month = c.addZero(date.getMonth() + 1);
        var day = c.addZero(date.getDate());
        return year + "年" + month + "月" + day + '日';
    };

    c.formatHHMMSS = function (date) {
        var hours = c.addZero(date.getHours());
        var minutes = c.addZero(date.getMinutes());
        var seconds = c.addZero(date.getSeconds());
        return hours + ":" + minutes + ":" + seconds;
    };


    c.addZero = function (num) {
        num = Number(num) || 0;
        return num < 10 ? '0' + num : num;
    };
}(window.chat = {});