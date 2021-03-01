export default {
  namespaced: true,
  state: {
    id: '',
    name: ''
  },
  mutations: {
    updateId (state, id) {
      state.id = id
    },
    updateName (state, name) {
      state.name = name
    }
  }
}
