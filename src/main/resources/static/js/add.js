/*jshint esversion: 6 */
var $app = new Vue({
  el: '#app',
  data: {
    title: 'sd fds收到',
    content: '456789456'
  },
  methods: {
    submit: function(event) {
      if(!this.title.length || !this.content.length) {
        alert("输入内容不能为空");
      } else {
        return true;
      }
      event.preventDefault();
    }
  }
});
