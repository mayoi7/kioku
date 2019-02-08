/*jshint esversion: 6 */
Date.prototype.format = function(format) {
   var date = {
          "M+": this.getMonth() + 1,
          "d+": this.getDate(),
          "h+": this.getHours(),
          "m+": this.getMinutes(),
          "s+": this.getSeconds(),
          "q+": Math.floor((this.getMonth() + 3) / 3),
          "S+": this.getMilliseconds()
   };
   if (/(y+)/i.test(format)) {
          format = format
                    .replace(RegExp.$1, (this.getFullYear() + '')
                    .substr(4 - RegExp.$1.length));
   }
   for (let k in date) {
          if (new RegExp("(" + k + ")").test(format)) {
                 format = format.replace(RegExp.$1,
                    RegExp.$1.length === 1 ? date[k] : ("00" + date[k])
                          .substr(("" + date[k]).length));
          }
   }
   return format;
};

var STRING_DATE_FORMAT = 'yyyy-MM-dd h:m:s';

var $app = new Vue({
  el: '#app',
  data: function() {
    $.get("/user/info", (data) => {
        if(data.code === 0) {
            this.user = data.data;
            $.get("/note/all/" + data.data.id, (d) => {
                if(d.code === 0) {
                    this.notes = d.data;
                    if(d.data.length > 0) this.current = d.data[0];

                    this.notes.forEach(elem => {
                        elem.date = new Date(elem.date).format(STRING_DATE_FORMAT);
                    });
                }
            });
        }
    });

    return {
        user : {},
        notes: {},
        current: {}
    };
  },
  methods: {
    tabChange: function(i) {
      this.$data.current = this.$data.notes[i];
    }
  }
});
