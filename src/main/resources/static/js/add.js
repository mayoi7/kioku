/*jshint esversion: 6 */
var $app = new Vue({
  el: '#app',
  data: function () {
      $.get("/user/info", (data) => {
          if(data.code === 0) {
              this.user = data.data;
          }
      });
      return {
          user: {},
          note: {
              title: '',
              content:  ''
          }
      }
  },
  methods: {
    submit: function(event) {
      if(!this.note.title.length || !this.note.content.length) {
          alert("输入内容不能为空");
      } else {
        $.post("/note/" + this.user.id, this.note, (data) => {
            if(data.code === 0) {
                window.location = "/";
            } else {
                alert(data.msg);
            }
        });
        return true;
      }
    }
  }
});
