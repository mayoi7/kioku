/*jshint esversion: 6 */
var app = new Vue({
    el: '#app',
    data: {
    'username': '',
    'password': ''
    },
    methods: {
      submit: function (e) {
        e.preventDefault();
        fetch('/test/msg').then(response => response.json()).then(data => {
            console.log(data);
        });
      }
    }
});
