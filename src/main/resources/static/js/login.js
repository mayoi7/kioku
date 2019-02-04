/*jshint esversion: 6 */
let app = new Vue({
    el: '#app',
    data: {
    'username': '',
    'password': ''
    },
    methods: {
      submit: function () {
        if(this.username.length === 0 || this.password.length === 0) {
            alert("用户名和密码不能为空");
            return false;
        }

        $.post("login/login", {
            username: this.username,
            password: this.password
        }, (data) => {
            if(data.code === 0) {
                window.location='/';
            }
            else {
                alert(data.msg);
                return false;
            }
        });
      },
    }
});
