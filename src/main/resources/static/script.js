// Função para enviar um novo post
document.getElementById("createPostForm").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita o comportamento padrão do formulário

    // Pegando os valores dos campos
    const title = document.getElementById("postTitle").value;
    const body = document.getElementById("postContent").value;
    const authorName = document.getElementById("authorName").value;
    const authorId = document.getElementById("authorId").value;

    // Valida se os campos obrigatórios estão preenchidos
    if (!title || !body || !authorName || !authorId) {
        alert("Por favor, preencha todos os campos obrigatórios.");
        return;
    }

    // Criando o objeto do post
    const postData = {
        title: title,
        body: body,
        author: {
            id: authorId,
            name: authorName
        }
    };

    try {
        // Enviando o post para o backend
        const response = await fetch("http://localhost:8080/posts", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(postData) // Envia os dados como JSON
        });

        // Verifica se a resposta é bem-sucedida
        if (response.ok) {
            // Como a resposta não tem corpo, apenas verifique se o status foi 201
            alert("Post criado com sucesso!");
            fetchPosts();  // Atualiza a lista de posts
            document.getElementById("createPostForm").reset();  // Limpa o formulário após o envio
        } else {
            const contentType = response.headers.get("Content-Type");
            if (contentType && contentType.includes("application/json")) {
                const data = await response.json();
                alert("Erro: " + (data.message || "Erro ao criar o post."));
            } else {
                const text = await response.text();
                alert("Erro desconhecido: " + text);
            }
        }
    } catch (error) {
        alert("Ocorreu um erro ao tentar criar o post: " + error.message);
    }
});




// Função para buscar posts do servidor
function fetchPosts() {
  const loadingStatus = document.getElementById('loadingStatus');
  loadingStatus.classList.add('show');

  fetch('http://localhost:8080/posts')
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro na requisição');
      }
      return response.json();
    })
    .then(posts => {
      const postsList = document.getElementById('postsList');
      postsList.innerHTML = ''; // Limpa a lista de posts

      posts.forEach(post => {
        const postItem = document.createElement('div');
        postItem.classList.add('post-item');

        // Formatar a data e hora sem os segundos
        const postDate = new Date(post.dateTime);
        const postFormattedDate = postDate.toLocaleDateString() + ' ' + postDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

        postItem.innerHTML = `
          <h3>${post.title}</h3>
          <p>${post.body}</p>
          <p>${post.author.name}</p>
          <p>${postFormattedDate}</p>

        `;

        if (post.comments && post.comments.length > 0) {
          const commentsSection = document.createElement('div');
          commentsSection.classList.add('comments-section');

          post.comments.forEach(comment => {
            const commentItem = document.createElement('div');
            commentItem.classList.add('comment-item');

            // Formatar a data do comentário
            const commentDate = new Date(comment.date);
            const commentFormattedDate = commentDate.toLocaleDateString() + ' ' + commentDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

            commentItem.innerHTML = `
              <p><strong>${comment.author.name || 'Anônimo'}:</strong> ${comment.text}</p>
              <p><small>${commentFormattedDate}</small></p>
            `;
            commentsSection.appendChild(commentItem);
          });

          postItem.appendChild(commentsSection);
        }

        const actionsDiv = document.createElement('div');
        actionsDiv.classList.add('actions');
        actionsDiv.innerHTML = `
          <button onclick="editPost('${post.id}')">Editar</button>
          <button onclick="deletePost('${post.id}')">Excluir</button>
        `;
        postItem.appendChild(actionsDiv);

        postsList.appendChild(postItem);
      });

      loadingStatus.classList.remove('show');
    })
    .catch(error => {
      console.error('Erro ao carregar os posts:', error);
      loadingStatus.innerText = 'Erro ao carregar os posts.';
    });
}


// Função para deletar um post
async function deletePost(postId) {
  console.log('Tentando excluir o post com ID:', postId);

  if (!postId) {
    console.error('ID inválido!');
    return; // Aborta a execução se o ID for inválido
  }

  // Confirmação antes de excluir
  if (confirm('Você tem certeza que deseja excluir este post?')) {
    try {
      const response = await fetch(`http://localhost:8080/posts/${postId}`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        throw new Error('Erro ao excluir o post');
      }

      alert('Post excluído com sucesso!');
      fetchPosts();  // Recarregar a lista de posts após a exclusão

    } catch (error) {
      console.error('Erro ao excluir o post:', error);
    }
  }
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


// Função para buscar usuários do servidor
function fetchUsers() {
  const loading = document.getElementById('loading');
  loading.classList.add('show');

  fetch('http://localhost:8080/users')
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro na requisição');
      }
      return response.json();
    })
    .then(users => {
      const userList = document.getElementById('userList');
      userList.innerHTML = ''; // Limpa a lista de usuários

      users.forEach(user => {
        const userItem = document.createElement('div');
        userItem.classList.add('user-item');

        userItem.innerHTML = `
          <h3>${user.name}</h3>
          <p>${user.email}</p>
          <p>ID: ${user.id}</p>
        `;

        const actionsDiv = document.createElement('div');
        actionsDiv.classList.add('actions');
        actionsDiv.innerHTML = `
          <button onclick="editUser(${user.id})">Editar</button>
          <button onclick="deleteUser('${user.id}')">Excluir</button>
        `;
        userItem.appendChild(actionsDiv);

        userList.appendChild(userItem);
      });

      loading.classList.remove('show');
    })
    .catch(error => {
      console.error('Erro ao carregar os usuários:', error);
      loading.innerText = 'Erro ao carregar os usuários.';
    });
}

// Função para deletar um usuário
async function deleteUser(userId) {
  console.log('Tentando excluir o usuário com ID:', userId);

  if (!userId) {
    console.error('ID inválido!');
    return;
  }

  if (confirm('Você tem certeza que deseja excluir este usuário?')) {
    try {
      const response = await fetch(`http://localhost:8080/users/${userId}`, { method: 'DELETE' });

      if (!response.ok) {
        throw new Error('Erro ao excluir o usuário');
      }

      alert('Usuário excluído com sucesso!');
      fetchUsers(); // Recarregar a lista de usuários após a exclusão
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
});
