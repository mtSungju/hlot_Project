import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue'

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: Home,
    },
    {
      path: '/login',
      component: () => import('@/views/login/Login.vue')
    },
    {
      path: '/company/companyList',
      component: () => import('@/views/company/CompanyList.vue')
    },
    {
      path: '/estimate/estimateList',
      component: () => import('@/views/estimate/EstimateList.vue')
    },
    {
      path: '/project/projectList',
      component: () => import('@/views/project/ProjectList.vue')
    },
    {
      path: '/contr/contrList',
      component: () => import('@/views/contr/ContrList.vue')
    },
    {
      path: '/cmmCode/cmmCodeList',
      component: () => import('@/views/cmmCode/CmmCodeList.vue')
    },
    {
      path: '/estimate/estimateCompare',
      component: () => import('@/views/estimate/EstimateCompare.vue')
    },
    {
      path: '/estimate/prodList',
      component: () => import('@/views/estimate/prodList.vue')
    }
  ],
})
