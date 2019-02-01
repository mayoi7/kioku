/*jshint esversion: 6 */
var re_email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var re_username = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){6,20}$/;
var re_password = /^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+){6,20}$/;

  var app = new Vue({
    el: '#app',
    data: {
      'errors': [false, false, false, false, false],
      'username': '',
      'password': '',
      'repeat': '',
      'email': '',
      'code': ''
    },
    methods: {
      submit: function(e) {
        this.validAll();
        if(this.errors[0] || this.errors[1] || this.errors[2] || this.errors[3] || this.errors[4]) {
          e.preventDefault();
        } else {
          return true;
        }
      },
      validUsername: function() {
        /*不匹配*/
        // if(!re_username.test(this.username)) this.errors[0] = true;
        this.errors[0] = (!re_username.test(this.username));

        /*异步校验用户名是否重复*/
      },
      validPassword: function() {
        this.errors[1] = (!re_password.test(this.password));
      },
      validRepeat: function() {
        this.errors[2] = (this.repeat != this.password);
      },
      validEmail: function () {
        this.errors[3] = (!re_email.test(this.email));
      },
      validCode: function() {
        /* 异步校验邀请码合法性 */
        this.errors[4] = (this.code != 'aaa');
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
