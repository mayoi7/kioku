/*jshint esversion: 6 */
Date.prototype.format = function(format)
{
    let o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(let k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length===1 ? o[k] :
                ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
};

let STRING_DATE_FORMAT = 'yyyy-MM-dd h:m:s';

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
        isShow: false,
        notes: {},
        current: {}
    };
  },
  methods: {
      switchBar: function(e) {
          this.isShow = !this.isShow;
      },
      tabChange: function(i) {
          this.$data.current = this.$data.notes[i];
      }
  }
});
