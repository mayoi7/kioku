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
   for (var k in date) {
          if (new RegExp("(" + k + ")").test(format)) {
                 format = format.replace(RegExp.$1,
                    RegExp.$1.length == 1 ? date[k] : ("00" + date[k])
                          .substr(("" + date[k]).length));
          }
   }
   return format;
};

var STRING_DATE_FORMAT = 'yyyy-MM-dd h:m:s';

var $app = new Vue({
  el: '#app',
  data: function() {
    // 模拟异步数据接收
    let notes =  [
      {
        id: 10,
        title: '神盾局好见风使舵海口警方的说法',
        content: 'aaaaaaaaaaaaaaaaa',
        date: 1499655375
      },
      {
        id: 11,
        title: 'bbbb',
        content: 'bbbbbbbbbbbbbbbbb',
        date: 1499655375
      },
      {
        id: 12,
        title: 'cccc',
        content: 'ccccccccccccccccc',
        date: 1499655375
      },
      {
        id: 13,
        title: 'dddd',
        content: 'ddddddddddddddddd',
        date: 1499655375
      },
      {
        id: 14,
        title: 'eeee',
        content: 'eeeeeeeeeeeeeeeee',
        date: 1499655375
      },
    ];
    let current = {};
    if(notes.length > 0) current = notes[0];
    notes.forEach(elem => {
      elem.date = new Date(elem.date).format(STRING_DATE_FORMAT);
    });

    return {
      notes: notes,
      current: current
    };
  },
  methods: {
    tabChange: function(i) {
      this.$data.current = this.$data.notes[i];
    }
  }
});
