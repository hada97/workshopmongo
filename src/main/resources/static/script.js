// Função para buscar posts do servidor
function fetchPosts() {
  // Exibe a mensagem de carregamento
  const loadingStatus = document.getElementById('loadingStatus');
  loadingStatus.classList.add('show');

  // Requisição GET para o servidor local
  fetch('http://localhost:8080/posts')  // Endpoint do servidor local
    .then(response => {
      console.log('Resposta do servidor:', response);  // Verifique a resposta
      if (!response.ok) {
        throw new Error('Erro na requisição');
      }
      return response.json();
    })
    .then(posts => {
      console.log('Posts recebidos:', posts);  // Verifique os posts retornados

      // Atualiza a lista de posts
      const postsList = document.getElementById('postsList');
      postsList.innerHTML = ''; // Limpa a lista atual de posts

      // Itera sobre os posts e cria os elementos para cada um
      posts.forEach(post => {
        const postItem = document.createElement('div');
        postItem.classList.add('post-item');

        // Criando o HTML para exibir o post
        postItem.innerHTML = `
          <h3>${post.title}</h3>
          <p><strong>Autor:</strong> ${post.author.name}</p>
          <p><strong>Data:</strong> ${new Date(post.dateTime).toLocaleString()}</p>
          <p>${post.body}</p>
        `;

        // Adiciona a seção de comentários, se houver
        if (post.comments && post.comments.length > 0) {
          const commentsSection = document.createElement('div');
          commentsSection.classList.add('comments-section');

          post.comments.forEach(comment => {
            const commentItem = document.createElement('div');
            commentItem.classList.add('comment-item');

            commentItem.innerHTML = `
              <p><strong>${comment.author.name || 'Anônimo'}:</strong> ${comment.text}</p>
              <p><small>Comentado em: ${new Date(comment.date).toLocaleString()}</small></p>
            `;

            commentsSection.appendChild(commentItem);
          });

          postItem.appendChild(commentsSection);
        }

        // Adiciona os botões de ação (editar e excluir) ao post
        const actionsDiv = document.createElement('div');
        actionsDiv.classList.add('actions');
        actionsDiv.innerHTML = `
          <button onclick="editPost(${post.id})">Editar</button>
          <button onclick="deletePost(${post.id})">Excluir</button>
        `;
        postItem.appendChild(actionsDiv);

        // Adiciona o post à lista
        postsList.appendChild(postItem);
      });

      // Esconde a mensagem de carregamento após a atualização
      loadingStatus.classList.remove('show');
    })
    .catch(error => {
      console.error('Erro ao carregar os posts:', error);
      loadingStatus.innerText = 'Erro ao carregar os posts.';
    });
}

// Função para buscar usuários do servidor
function fetchUsers() {
  // Exibe a mensagem de carregamento
  const loading = document.getElementById('loading');
  loading.classList.add('show');

  // Requisição GET para o servidor local
  fetch('http://localhost:8080/users')  // Endpoint do servidor local
    .then(response => {
      console.log('Resposta do servidor (usuários):', response);  // Verifique a resposta
      if (!response.ok) {
        throw new Error('Erro na requisição');
      }
      return response.json();
    })
    .then(users => {
      console.log('Usuários recebidos:', users);  // Verifique os usuários retornados

      // Atualiza a lista de usuários
      const userList = document.getElementById('userList');
      userList.innerHTML = ''; // Limpa a lista atual de usuários

      // Itera sobre os usuários e cria os elementos para cada um
      users.forEach(user => {
        const userItem = document.createElement('div');
        userItem.classList.add('user-item');

        // Criando o HTML para exibir o usuário
        userItem.innerHTML = `
          <h3>${user.name}</h3>
          <p><strong>Email:</strong> ${user.email}</p>
        `;

        // Adiciona o usuário à lista
        userList.appendChild(userItem);
      });

      // Esconde a mensagem de carregamento após a atualização
      loading.classList.remove('show');
    })
    .catch(error => {
      console.error('Erro ao carregar os usuários:', error);
      loading.innerText = 'Erro ao carregar os usuários.';
    });
}

// Chama a função pela primeira vez quando a página carregar
document.addEventListener('DOMContentLoaded', () => {
  fetchPosts();  // Carrega os posts
  fetchUsers();  // Carrega os usuários
  setInterval(fetchPosts, 10000); // Atualiza os posts a cada 10 segundos
  setInterval(fetchUsers, 10000); // Atualiza os usuários a cada 10 segundos
});
