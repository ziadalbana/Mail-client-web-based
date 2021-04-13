import Vue from 'vue'
import VueRouter from 'vue-router'
import signUp from '../views/signUp.vue'
import signIn from '../views/signIn.vue'
import compose from '../views/compose.vue'
import signedIn from '../views/signedIn.vue'
import settings from '../views/settings.vue'
import contacts from '../views/contacts'

Vue.use(VueRouter)

const routes = [
  {
    path: '/contacts',
    name: 'contacts',
    component: contacts
  },
  {
    path: '/signUp',
    name: 'signUp',
    component: signUp
  },
  {
    path: '/compose',
    name: 'compose',
    component: compose
  },
  {
    path:'/',
    name: 'signIn',
    component: signIn
  },
  {
    path: '/signedIn',
    name: 'signedIn',
    component: signedIn
  },
  {
    path: '/settings',
    name: 'settings',
    component: settings
  },
]

const router = new VueRouter({
  routes
})

export default router
