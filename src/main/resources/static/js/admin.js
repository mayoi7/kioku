/*jshint esversion: 6 */

var crr = 1;

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
                online: 12544,
                reg: 35476,
                note: 85142
            },
        };
    },
    mounted() {
        crr = 1;
    }
};

let user = {
    template: `
  <div class="user">
    <div class="search-bar" style="text-align: center; margin: 12px auto;">
      <input type="text" class="text-input" name="username" v-model="name"
        autocomplete="off" placeholder="查询的用户名这里输入">
      <button class="search-btn div-btn">
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
            <td>{{user.regDate}}</td>
            <td>{{user.code}}</td>
          </tr>
        </tbody>
    </table>
    <ul class="pagination">
      <li><a href="javascript:;" @click="go_first_page">首页</a></li>
      <li v-for="num in pager.pages">
        <a href="javascript:;"
        v-bind:class="{active: pager.crt_page == num}" @click="switch_page(num)">{{num}}</a>
      </li>
      <li><a href="javascript:;" @click="go_last_page">尾页</a></li>
  </ul>
  `,
    data() {
        return {
            name: '',
            users: [
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                },
                {
                    id: '123',
                    username: 'aaaa',
                    role: 10,
                    noteCount: 52,
                    regDate: '2018-09-09 15:24:01',
                    code: '5scf7d'
                }
            ],
            pager: {  // ajax, update 'users' synchronously
                count: 15,
                crt_page: 1,
                pages: [1, 2, 3, 4, 5]
            }
        };
    },
    methods: {
        switch_page: function(tar) {
            /* 通过ajax获取新页内容，然后修改users数据 */
            // ...
            let arr = this.pager.pages;
            let crt = this.pager.crt_page;

            if(arr.length < 5 || tar === arr[2]) {
                this.pager.crt_page = tar;
                return;
            }

            if(tar > crt && arr[4] + (tar - crt) > 15) {
                // 向后翻 且页码不够的情况
                this.pager.pages = [this.pager.count-4, this.pager.count-3,
                    this.pager.count-2, this.pager.count-1, this.pager.count];
                this.pager.crt_page = tar;
                return;
            } else if(tar < crt && arr[0] - (crt - tar) < 1) {
                // 向前翻 且页码不够的情况
                this.pager.pages = [1, 2, 3, 4, 5];
                this.pager.crt_page = tar;
                return;
            } else {
                if(this.pager.count - tar < 3) {
                    this.pager.pages = [this.pager.count-4, this.pager.count-3,
                        this.pager.count-2, this.pager.count-1, this.pager.count];
                } else if(tar < 3) {
                    this.pager.pages = [1, 2, 3, 4, 5];
                } else {
                    this.pager.pages = [tar-2, tar-1, tar, tar+1, tar+2];
                }

                this.pager.crt_page = tar;
                return;
            }
        },
        go_first_page: function() {
            /* 通过ajax获取新页内容，然后修改users数据 */
            // ...

            if(this.pager.count > 5) {
                this.pager.pages = [1, 2, 3, 4, 5];
            }
            this.pager.crt_page = 1;
        },
        go_last_page: function() {
            /* 通过ajax获取新页内容，然后修改users数据 */
            // ...

            if(this.pager.count > 5) {
                this.pager.pages = [this.pager.count-4, this.pager.count-3,
                    this.pager.count-2, this.pager.count-1, this.pager.count];
            }
            this.pager.crt_page = this.pager.count;
        }
    },
    mounted() {
        crr = 2;
    }
};

let role = {
    template: `
  <div class="role">
    <div class="search-bar" style="text-align: center; margin: 12px auto;">
      <input type="text" class="text-input" name="name" v-model="name"
      autocomplete="off" placeholder="查询的用户名在这里输入">
      <button class="search-btn div-btn">
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
            <td>{{user.regDate}}</td>
            <td>{{user.code}}</td>
          </tr>
        </tbody>
    </table>
    <div class="option" style="margin-top: 48px;">
      <div class="title">操作列表<hr></div>
    </div>
    <ul class="opt-list">
      <li><a href="javascript:;">销号<hr></a></li>
      <li><a href="javascript:;">锁定<hr></a></li>
      <li><a href="javascript:;">恢复<hr></a></li>
      <li><a href="javascript:;">授权<hr></a></li>
    </ul>
  </div>
  `,
    data() {
        return {
            name: '',
            user: []
        };
    },
    mounted() {
        crr = 3;
    }
};

let manage = {
    template: '',
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
        { path: '/manage', component: manage }
    ]
});

var $app = new Vue({
    el: '#app',
    data: function() {
        return {
            current: 1,
            users:  // ajax
                [
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    },
                    {
                        id: '123',
                        username: 'aaaa',
                        role: 10,
                        noteCount: 52,
                        regDate: '2018-09-09 15:24:01',
                        code: '5scf7d'
                    }
                ],

        };
    },
    methods: {
        // all
        select: function(idx) {
            this.current = idx;
        },
        // user

    },
    router: router,
    mounted () {
        this.current = crr;
    }
});