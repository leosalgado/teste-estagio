<template>
  <div class="container">
    <h1>Busca de Operadoras</h1>
    <div class="search">
      <input
        type="text"
        v-model="searchTerm"
        @keyup.enter="buscarOperadora"
        placeholder="Digite o nome da operadora"
      />
      <button @click="buscarOperadora">Buscar</button>
    </div>
    <table v-if="resultado.length > 0">
      <thead>
        <tr>
          <th>Nome Fantasia</th>
          <th>Razão Social</th>
          <th>CNPJ</th>
          <th>Modalidade</th>
          <th>Cidade</th>
          <th>UF</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in resultado" :key="index">
          <td>{{ item.Nome_Fantasia }}</td>
          <td>{{ item.Razao_Social }}</td>
          <td>{{ item.CNPJ }}</td>
          <td>{{ item.Modalidade }}</td>
          <td>{{ item.Cidade }}</td>
          <td>{{ item.UF }}</td>
        </tr>
      </tbody>
    </table>

    <p v-else-if="buscou">Nenhum resultado encontrado.</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const searchTerm = ref('')
const resultado = ref([])
const buscou = ref(false)

const buscarOperadora = async () => {
  if (searchTerm.value.trim() === '') {
    resultado.value = []
    buscou.value = true
    return
  }

  try {
    const response = await axios.get('http://localhost:8000/search', {
      params: {
        name: searchTerm.value
      }
    })
    resultado.value = response.data
    buscou.value = true
  } catch (error) {
    console.error('Erro ao buscar operadora:', error)
  }
}
</script>

<style scoped>
h1 {
  display: flex;
  justify-content: center;
  font-size: 300%;
  font-weight: bold;
}
.search {
  display: flex;
  justify-content: center;
}
.container {
  /* max-width: 800px; */
  max-width: 1200px;
  margin: auto;
  padding: 1rem;
  font-family: sans-serif;
}
input {
  padding: 0.5rem;
  width: 100%;
  margin-right: 0.5rem;
}
button {
  padding: 0.5rem 1rem;
  cursor: pointer;
}
table {
  margin-top: 1rem;
  width: 100%;
  border-collapse: collapse;
  background-color: #111;
}
th, td {
  /* border: 1px solid #ccc; */
  border: 1px solid grey;
  color: #f0f0f0;
  padding: 0.5rem;
  text-align: left;
}
</style>
