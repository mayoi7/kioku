/*jshint esversion: 6 */
const re_email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const re_username = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){6,20}$/;
const re_password = /^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+){6,20}$/;

var app = new Vue({
    el: '#app',
    data: {
      'errors':
          [
              {isErr: false, msg: ''},
              {isErr: false, msg: ''},
              {isErr: false, msg: ''},
              {isErr: false, msg: ''},
              {isErr: false, msg: ''}
          ],
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
        if (this.errors[0].isErr ||
            this.errors[1].isErr ||
            this.errors[2].isErr ||
            this.errors[3].isErr ||
            this.errors[4].isErr) {
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
        this.errors[0].isErr = (!re_username.test(this.user.username));
        this.errors[0].msg = '用户名不合法';

        /*异步校验用户名是否重复*/
          $.get("/login/detect/name/" + this.user.username, (data) => {
            // 假如用户名重复（code为1），将错误标志置为真
            if(data.code === 0) {
                this.errors[0].msg = '可用';
                this.errors[0].isErr = false;
            } else {
                this.errors[0].msg = '用户名被使用';
                this.errors[0].isErr = true;
            }
          });
      },
      validPassword: function() {
        this.errors[1].isErr = (!re_password.test(this.user.password));
      },
      validRepeat: function() {
        this.errors[2].isErr = (this.user.repeat !== this.user.password);
      },
      validEmail: function () {
        this.errors[3].isErr = (!re_email.test(this.user.email));
      },
      validCode: function() {
        /* 异步校验邀请码合法性 */
          $.get("/login/detect/code/" + this.user.code, (data) => {
              // 假如用户名重复（code为1），将错误标志置为真
              this.errors[4].isErr = (data.code !== 0);
          });
      },
      validAll: function() {
        this.errors = [
            {isErr: false, msg: ''},
            {isErr: false, msg: ''},
            {isErr: false, msg: ''},
            {isErr: false, msg: ''},
            {isErr: false, msg: ''}
        ];
        this.validUsername();
        this.validPassword();
        this.validRepeat();
        this.validEmail();
        this.validCode();
      }
    }
  });
