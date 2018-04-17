$(function() {
    function track(options) {
        var _uid = options.uid,
            _uname = options.uname;

        window.QBAO_UT_GV = window.QBAO_UT_GV || {};
        window.QBAO_UT_GV['_uid'] = _uid;
        window.QBAO_UT_GV['_uname'] = _uname;
        var s = document.createElement('script'),
            e = document.body.getElementsByTagName('script')[0];
        s.type = 'text/javascript';
        s.async = true;
        s.charset = 'utf-8';
        e.parentNode.insertBefore(s, e);
        s.src = 'http://dig.qbao.com/wap-site/js/user_tracker_dig.js';
    }

    track({
        uid: '',
        uname: ''
    });
});
