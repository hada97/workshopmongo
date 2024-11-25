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

    users.forEach(user => {
      const userItem = document.createElement('div');
      userItem.classList.add('user-item');

      // Criando o HTML para exibir o usuário
      userItem.innerHTML = `
        <h3>${user.name}</h3>
        <p><strong>Email:</strong> ${user.email}</p>
        <p><strong>ID:</strong> ${user.id}</p>
      `;

      // Criando os botões de editar e excluir
      const actionsDiv = document.createElement('div');
      actionsDiv.classList.add('actions');
      actionsDiv.innerHTML = `
        <button onclick="editUser(${user.id})">Editar</button>
        <button onclick="deleteUser('${user.id}')">Excluir</button>
      `;

      // Adiciona os botões de ação ao item de usuário
      userItem.appendChild(actionsDiv);

      // Adiciona o usuário à lista
      userList.appendChild(userItem);
    });


      loading.classList.remove('show');
    })
    .catch(error => {
      console.error('Erro ao carregar os usuários:', error);
      loading.innerText = 'Erro ao carregar os usuários.';
    });
}


// Função para criar um novo usuário
function createUser(event) {
  event.preventDefault();  // Previne o comportamento padrão do form (recarregar a página)

  // Pega os valores dos campos de nome e email
  const name = document.getElementById('username').value;
  const email = document.getElementById('email').value;

  // Cria um objeto de usuário com os dados do formulário
  const userData = { name, email };

  // Envia os dados para o servidor via POST
  fetch('http://localhost:8080/users', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData),
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro ao criar o usuário');
      }
      return response.json();
    })
    .then(newUser => {
      console.log('Novo usuário criado:', newUser);

      // Limpa o formulário
      document.getElementById('createUserForm').reset();

      // Atualiza a lista de usuários
      fetchUsers();

      // Exibe uma mensagem de sucesso (pop-up simples)
      alert('Usuário cadastrado com sucesso!');
    })
    .catch(error => {
      console.error('Erro ao criar o usuário:', error);
    });
}



async function deleteUser(userId) {
  console.log('Tentando excluir o usuário com ID:', userId);

  if (!userId) {
    console.error('ID inválido!');
    return; // Aborta a execução
  }

  // Confirmação antes de excluir
  if (confirm('Você tem certeza que deseja excluir este usuário?')) {
    try {
      const response = await fetch(`http://localhost:8080/users/${userId}`, { method: 'DELETE' });

      if (!response.ok) {
        throw new Error('Erro ao excluir o usuário');
      }

      alert('Usuário excluído com sucesso!');
      fetchUsers();  // Recarregar lista de usuários após a exclusão

    } catch (error) {
      console.error('Erro ao excluir o usuário:', error);
    }
  }
}




// Chama a função pela primeira vez quando a página carregar
document.addEventListener('DOMContentLoaded', () => {
  fetchPosts();  // Carrega os posts
  fetchUsers();  // Carrega os usuários
  setInterval(fetchPosts, 10000); // Atualiza os posts a cada 10 segundos
  setInterval(fetchUsers, 10000); // Atualiza os usuários a cada 10 segundos

  // Adiciona o evento de envio do formulário para criar um usuário
  const createUserForm = document.getElementById('createUserForm');
  createUserForm.addEventListener('submit', createUser);
});


