import VueRouter from 'vue-router';
const routes =[
    {
        path:'/',
        name:'nav',
        component:()=>import('../components/Nav')
    },
    {
        path:'/Login',
        name:'login',
        component:()=>import('../components/Login')
    },
    {
        path: '/index',
        name: 'index',
        component:()=>import('../components/index'),
        children:[
            {
                path:'/Home',
                name:'home',
                meta:{
                  title:'个人中心',
                },
                component:()=>import('../components/Home')
            }
            // {
            //     path:'/Admin',
            //     name:'admin',
            //     meta:{
            //         title:'管理员管理'
            //     },
            //     component:()=>import('../components/admin/AdminManage.vue')
            // },
            // {
            //     path:'/User',
            //     name:'user',
            //     meta:{
            //         title:'用户管理'
            //     },
            //     component:()=>import('../components/user/UserManage.vue')
            // }
        ]
    }
]
const router = new VueRouter({
    mode:'history',
    routes
})
export function resetRouter() {  //将routes直接清空，以防止store里合并路由时route重复
    router.matcher = new VueRouter({
        mode:'history',
        routes: []  //清空
    }).matcher
}

const VueRouterPush = VueRouter.prototype.push //防止报错
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export default router;