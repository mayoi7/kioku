/*jshint esversion: 6 */

let crr = 1;

let index = {
    template: `
  <div class="index">
    <ul>
      <li>
        <div class="desp">在线人数</div>
        <div class="data">{{website.online}}</div>
      </li>
      <li>
        <div class="desp">注册人数</div>
        <div class="data">{{website.reg}}</div>
      </li>
      <li>
        <div class="desp">日记总数</div>
        <div class="data">{{website.note}}</div>
      </li>
    </ul>
  </div>
  `,
    data() {
        return {
            website: {  // ajax
                online: 0,
                reg: 0,
                note: 0
            },
        };
    },
    mounted() {
        // 获取在线人数
        $.get("/admin/count/online", (data) => {
            if(data.code === 0) {
                this.$data.website.online = data.data;
            }
        });

        // 获取注册人数(总用户数)
        $.get("/admin/count/user", (data) => {
            if(data.code === 0) {
                this.$data.website.reg = data.data;
            }
        });

        // 获取笔记总数
        $.get("/admin/count/note", (data) => {
            if(data.code === 0) {
                this.$data.website.note = data.data;
            }
        });
        crr = 1;
    }
};

let user = {
    template: `
  <div class="user">
    <div class="search-bar" style="text-align: center; margin: 12px auto;">
      <input type="text" class="text-input" name="username" v-model="name"
        autocomplete="off" placeholder="查询的用户名这里输入">
      <button class="search-btn div-btn" @click="queryUser">
          <svg fill="currentColor" width="30" height="30" version="1.1" x="0px" y="0px" viewBox="0 0 1000 1000" enable-background="new 0 0 1000 1000" xml:space="preserve">
            <g><path d="M990,919L724.5,652.1c51.1-67.3,81.4-151.2,
              81.4-242.2c0-220.5-178.5-400-397.9-400C188.5,10,10,189.4,10,410c0,
              220.6,178.5,400,398,400c93,0,178.7-32.3,246.6-86.2L919.4,990L990,
              919z M408,720C237.9,720,99.6,580.9,99.6,410S237.9,100,408,100c170,
              0,308.4,139,308.4,310C716.4,580.9,578,720,408,720z"></path>
            </g>
          </svg>
        </button>
    </div>
    <table class="table table-hover beauty-table user-table">
      <thead>
        <th>id</th>
        <th>用户名</th>
        <th>权限</th>
        <th>日记总数</th>
        <th>注册时间</th>
        <th>邀请码</th>
      </thead>
      <tbody>
          <tr v-for="user in users">
            <td>{{user.id}}</td>
            <td>{{user.username}}</td>
            <td>
              <span class="role locked" v-if="user.role == 10">锁定</span>
              <span class="role normal" v-if="user.role == 11">普通用户</span>
              <span class="role admin" v-if="user.role == 12">管理员</span>
              <span class="role sp-admin" v-if="user.role == 13">超级管理员</span>
            </td>
            <td>{{user.noteCount}}</td>
            <td style="font-size: 15px; padding: 0;">{{user.regDate}}</td>
            <td>{{user.code}}</td>
          </tr>
        </tbody>
    </table>
    <ul class="pagination">
      <li><a href="javascript:;" @click="go_first_page">首页</a></li>
      <li v-for="num in pager.pages">
        <a href="javascript:;"
        v-bind:class="{active: pager.currentPage == num}" @click="switch_page(num)">{{num}}</a>
      </li>
      <li><a href="javascript:;" @click="go_last_page">尾页</a></li>
  </ul>
  `,
    data() {
        return {
            name: '',
            users: [],
            pager: {  // ajax, update 'users' synchronously
                count: 1,
                currentPage: 1,
                pages: [1]
            }
        };
    },
    methods: {
        queryUser() {
            if(this.$data.name === "") {
                alert("不能输入空值");
                return;
            }
            $.get("api/user/" + this.$data.name, (data) => {
                if(data.code === 0) {
                    this.$data.users = [data.data];
                }
            });
        },
        switch_page: function(tar) {
            /* 通过ajax获取新页内容，然后修改users数据 */
            // ...
            let arr = this.pager.pages;
            let crt = this.pager.currentPage;

            if(arr.length < 5 || tar === arr[2]) {
                this.pager.currentPage = tar;
                return;
            }

            if(tar > crt && arr[4] + (tar - crt) > 15) {
                // 向后翻 且页码不够的情况
                this.pager.pages = [this.pager.count-4, this.pager.count-3,
                    this.pager.count-2, this.pager.count-1, this.pager.count];
                this.pager.currentPage = tar;
            } else if(tar < crt && arr[0] - (crt - tar) < 1) {
                // 向前翻 且页码不够的情况
                this.pager.pages = [1, 2, 3, 4, 5];
                this.pager.currentPage = tar;
            } else {
                if(this.pager.count - tar < 3) {
                    this.pager.pages = [this.pager.count-4, this.pager.count-3,
                        this.pager.count-2, this.pager.count-1, this.pager.count];
                } else if(tar < 3) {
                    this.pager.pages = [1, 2, 3, 4, 5];
                } else {
                    this.pager.pages = [tar-2, tar-1, tar, tar+1, tar+2];
                }
                this.pager.currentPage = tar;
            }
        },
        go_first_page: function() {
            /* 通过ajax获取新页内容，然后修改users数据 */
            // ...

            if(this.pager.count > 5) {
                this.pager.pages = [1, 2, 3, 4, 5];
            }
            this.pager.currentPage = 1;
        },
        go_last_page: function() {
            /* 通过ajax获取新页内容，然后修改users数据 */
            // ...

            if(this.pager.count > 5) {
                this.pager.pages = [this.pager.count-4, this.pager.count-3,
                    this.pager.count-2, this.pager.count-1, this.pager.count];
            }
            this.pager.currentPage = this.pager.count;
        }
    },
    mounted() {
        $.get("/admin/query/user/1", (data) => {
            if(data.code === 0) {
                this.$data.users = data.data;
            }
        });

        $.get("/admin/count/user/page", (data) => {
            if(data.code === 0) {
                this.$data.pager = data.data;
            }
        });
        crr = 2;
    }
};

let role = {
    template: `
  <div class="role">
    <div class="search-bar" style="text-align: center; margin: 12px auto;">
      <input type="text" class="text-input" name="name" v-model="name"
      autocomplete="off" placeholder="查询的用户名在这里输入">
      <button class="search-btn div-btn" @click="queryUser">
          <svg fill="currentColor" width="30" height="30" version="1.1" x="0px" y="0px" viewBox="0 0 1000 1000" enable-background="new 0 0 1000 1000" xml:space="preserve">
            <g><path d="M990,919L724.5,652.1c51.1-67.3,81.4-151.2,
              81.4-242.2c0-220.5-178.5-400-397.9-400C188.5,10,10,189.4,10,410c0,
              220.6,178.5,400,398,400c93,0,178.7-32.3,246.6-86.2L919.4,990L990,
              919z M408,720C237.9,720,99.6,580.9,99.6,410S237.9,100,408,100c170,
              0,308.4,139,308.4,310C716.4,580.9,578,720,408,720z"></path>
            </g>
          </svg>
        </button>
    </div>
    <hr style="margin-top: 6px;">
    <table class="table search-result">
      <thead>
        <th>id</th>
        <th>用户名</th>
        <th>权限</th>
        <th>日记总数</th>
        <th>注册时间</th>
        <th>邀请码</th>
      </thead>
      <tbody>
          <tr>
            <td>{{user.id}}</td>
            <td>{{user.username}}</td>
            <td>
              <span class="role locked" v-if="user.role == 10">锁定</span>
              <span class="role normal" v-if="user.role == 11">普通用户</span>
              <span class="role admin" v-if="user.role == 12">管理员</span>
              <span class="role sp-admin" v-if="user.role == 13">超级管理员</span>
            </td>
            <td>{{user.noteCount}}</td>
            <td style="font-size: 15px; padding: 0;">{{user.regDate}}</td>
            <td>{{user.code}}</td>
          </tr>
        </tbody>
    </table>
    <div class="option" style="margin-top: 48px;">
      <div class="title">操作列表<hr></div>
    </div>
    <ul class="opt-list">
      <li><a href="javascript:;" @click="deleteUser">销号<hr></a></li>
      <li><a href="javascript:;" @click="lockUser">锁定<hr></a></li>
      <li><a href="javascript:;" @click="resetUser">重置<hr></a></li>
      <li><a href="javascript:;" @click="authorizeUser">授权<hr></a></li>
    </ul>
  </div>
  `,
    data() {
        return {
            name: '',
            user: {}
        };
    },
    methods: {
        queryUser() {
            if(this.$data.name === "") {
                alert("不能输入空值");
                return;
            }
            $.get("api/user/" + this.$data.name, (data) => {
                if(data.code === 0) {
                    this.$data.user = data.data;
                }
            });
        },
        deleteUser() {
            $.post("/admin/user/" + this.$data.name, {_method: "delete"}, (data) => {
                if(data.code === 0) {
                    this.$router.go(0);
                } else {
                    alert("删除失败：" + data.msg);
                }
            });
        },
        lockUser() {
            $.post("/admin/lock/" + this.$data.name, (data) => {
                if(data.code === 0) {
                    this.$router.go(0);
                } else {
                    alert("锁定失败：" + data.msg);
                }
            });
        },
        resetUser() {
            $.post("/admin/reset/" + this.$data.name, (data) => {
                if(data.code === 0) {
                    this.$router.go(0);
                } else {
                    alert("重置失败：" + data.msg);
                }
            });
        },
        authorizeUser() {
            $.post("/admin/authorize/" + this.$data.name, (data) => {
                if(data.code === 0) {
                    this.$router.go(0);
                } else {
                    alert("授权失败：" + data.msg);
                }
            });
        }
    },
    mounted() {
        crr = 3;
    }
};

let code = {
    template: `
    <div class="code">
          <div class="code-option">
            <button class="div-btn add">添加一个</button>
            <button class="div-btn add">添加十个</button>
          </div>
          <table class="table table-hover beauty-table code-table">
            <thead>
              <th>编号</th>
              <th>邀请码</th>
              <th>使用者</th>
              <th>使用时间</th>
            </thead>
            <tbody>
              <tr v-for='item in codes'>
                <td :class="{new :item.ifNew}">{{item.id}}</td>
                <td>{{item.code}}</td>
                <td>
                  <span v-if="item.user == '' || item.user == null" class="unused">未使用</span>
                  <span v-else>{{item.user}}</span>
                </td>
                <td>{{item.date}}</td>
              </tr>
            </tbody>
          </table>
        </div>
`,
    data() {
        return {
            codes: {}
        };
    },
    mounted() {
        crr = 4;
    }
};

// (resolve) => require(['../vue/index-admin.vue'], resolve)

let router = new VueRouter({
    routes: [
        { path: '/', component:  index},
        { path: '/user', component: user },
        { path: '/role', component: role },
        { path: '/code', component: code }
    ]
});

var $app = new Vue({
    el: '#app',
    data: function() {
        // 获取当前用户信息
        $.get("/user/info", (data) => {
            if(data.code === 0) {
                this.user = data.data;
            }
        });
        return {
            user: {},
            'isShow': false, // 是否显示用户信息操作的下拉栏
            current: 1  // 当前选单编号
        };
    },
    methods: {
        select: function(idx) {
            this.current = idx;
        },
        switchBar: function() {
            this.isShow = !this.isShow;
        }

    },
    router: router,
    mounted () {
        this.current = crr;
    }
});
