var $app = new Vue({
    el: '#app',
    data: function () {
        $.get("/user/info", (data) => {
            if(data.code === 0) {
                this.user = data.data;
            }
        });

        return {
            'user': {}
        };
    }
});