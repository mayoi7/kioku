/*jshint esversion: 6 */
var app = new Vue({
    el: '#app',
    data: {
    'username': '',
    'password': ''
    },
    methods: {
      submit: function (e) {
        if(this.username.length === 0 || this.password.length === 0) {
            alert("用户名和密码不能为空");
            e.preventDefault();
            return;
        }

        $.post("login/login", {
            username: this.username,
            password: this.password
        }, (data) => {
            console.log(data);
            e.preventDefault();
        });

        e.preventDefault();
      }
    }
});
