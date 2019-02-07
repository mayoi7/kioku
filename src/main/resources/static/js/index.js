var $app = new Vue({
    el: '#app',
    data: function () {
        $.get("/user/info", (data) => {
            if(data.code === 0) {
                this.user = data.data;
            }
        });

        return {
            'user': {},
            'isShow': false // 是否显示用户信息操作的下拉栏
        };
    },
    methods: {
        switchBar: function(e) {
            this.isShow = !this.isShow;
        }
    }
});