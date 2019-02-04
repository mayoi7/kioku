/*jshint esversion: 6 */
const re_email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const re_username = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){6,20}$/;
const re_password = /^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+){6,20}$/;

var app = new Vue({
    el: '#app',
    data: {
      'errors': [false, false, false, false, false],
      'user': {
          username: '',
          password: '',
          repeat: '',
          email: '',
          code: ''
      }
    },
    methods: {
      submit: function(e) {
        this.validAll();
        if(this.errors[0] || this.errors[1] || this.errors[2] || this.errors[3] || this.errors[4]) {
          return false;
        } else {
          $.post("login/register", this.user, (data) => {
            if(data.code === 0) {
              // 跳转首页
              window.location("/");
            } else {

            }
          });
          return true;
        }
      },
      validUsername: function() {
        /*不匹配*/
        // if(!re_username.test(this.username)) this.errors[0] = true;
        this.errors[0] = (!re_username.test(this.user.username));

        /*异步校验用户名是否重复*/
          $.get("/login/detect/name/" + this.user.username, (data) => {
            // 假如用户名重复（code为1），将错误标志置为真
            this.errors[0] = (data.code !== 0);
          });
      },
      validPassword: function() {
        this.errors[1] = (!re_password.test(this.user.password));
      },
      validRepeat: function() {
        this.errors[2] = (this.user.repeat !== this.user.password);
      },
      validEmail: function () {
        this.errors[3] = (!re_email.test(this.user.email));
      },
      validCode: function() {
        /* 异步校验邀请码合法性 */
          $.get("/login/detect/code/" + this.user.code, (data) => {
              // 假如用户名重复（code为1），将错误标志置为真
              this.errors[4] = (data.code !== 0);
          });
      },
      validAll: function() {
        this.errors = [false, false, false, false, false];
        this.validUsername();
        this.validPassword();
        this.validRepeat();
        this.validEmail();
        this.validCode();
      }
    }
  });
