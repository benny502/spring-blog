export default [
    {
      path: '/',
      redirect: {
        name: 'dashboard.home'
      }
    },
    {
      path: '/home',
      name: 'dashboard.home',
      component: () => import('../../components/HelloWorld.vue')
    }
  ]