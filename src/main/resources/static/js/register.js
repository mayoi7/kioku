/*jshint esversion: 6 */
const re_email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const re_username = /^[a-zA-Z]([a-zA-Z0-9]|[._]){5,20}$/;
const re_password = /^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+){5,20}$/;

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
          $.post("/login/register", this.user, (data) => {
            if(data.code === 0) {
              // 跳转首页
                window.location='/';
            } else {

            }
          });
          return true;
        }
      },
      validUsername: function() {
        /*不匹配*/
        if(!re_username.test(this.user.username)) {
            // 用户名格式不正确
            this.errors[0].isErr = true;
            this.errors[0].msg = '格式不正确的用户名';
            return false;
        }


        /*异步校验用户名是否重复*/
          $.get("/login/detect/name/" + this.user.username, (data) => {
            // 假如用户名重复（code为1），将错误标志置为真
            if(data.code === 0) {
                this.errors[0].isErr = false;
                this.errors[0].msg = '可用';
            } else {
                this.errors[0].isErr = true;
                this.errors[0].msg = data.msg;
            }
          });
      },
      validPassword: function() {
          if(re_password.test(this.user.password)) {
              // 符合密码格式
              this.errors[1].isErr = false;
              this.errors[1].msg = "可用";
          } else {
              this.errors[1].isErr = true;
              this.errors[1].msg = "不符合规范的密码";
          }
      },
      validRepeat: function() {
          if(this.user.repeat !== this.user.password) {
              this.errors[2].isErr = true;
              this.errors[2].msg = '不一致的两次输入';
          } else {
              this.errors[2].isErr = false;
              this.errors[2].msg = "一致";
          }
      },
      validEmail: function () {
          if(re_email.test(this.user.email)) {
              this.errors[3].isErr = false;
              this.errors[3].msg = "可用";
          } else {
              this.errors[3].isErr = true;
              this.errors[3].msg = "格式不正确的邮箱";
          }
      },
      validCode: function() {
        /* 异步校验邀请码合法性 */
          $.get("/login/detect/code/" + this.user.code, (data) => {
              // 假如用户名重复（code为1），将错误标志置为真
              if(data.code === 0) {
                  // 邀请码可用
                  this.errors[4].isErr = false;
                  this.errors[4].msg = "可用";
              } else {
                  // 邀请码不可用
                  this.errors[4].isErr = true;
                  this.errors[4].msg = data.msg;
              }
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
