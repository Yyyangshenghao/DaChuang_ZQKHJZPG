import vue from 'vue'
import Vuex from 'vuex'  //状态管理器
import router, {resetRouter} from "../router"
import createPersistedState from 'vuex-persistedstate'  //解决刷新后vuex丢失路由数据问题
vue.use(Vuex)

function addNewRoute(menuList) {
    console.log(menuList)
    let routes = router.options.routes  //获取路由列表
    console.log(routes)
    routes.forEach(routeItem=>{  //组装路由
        if(routeItem.path=="/index"){
            menuList.forEach(menu=>{
                let childRoute =  {
                    path:'/'+menu.menuclick,
                    name:menu.menuname,
                    meta:{
                        title:menu.menuname
                    },
                    component:()=>import('../components/'+menu.menucomponent)
                }

                routeItem.children.push(childRoute)
            })
        }
    })

    resetRouter()
    router.addRoutes(routes)  //合并路由
}

export default new Vuex.Store({
    state: {
        menu: []
    },
    mutations: {
        setMenu(state,menuList) {
            state.menu = menuList

            addNewRoute(menuList)
        },
        setRouter(state,menuList) {
            state.menu = menuList

            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            return state.menu
        }
    },
    plugins:[createPersistedState()]  //解决刷新后vuex丢失路由数据问题
})