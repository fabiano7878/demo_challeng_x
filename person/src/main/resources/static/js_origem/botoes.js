function foundPerson(event){
   event.preventDefault();
   let url = "http:/person/search?";
   let birthdateResponse = '';

   if (document.querySelector("#personFormSearch") != null){
         path ='/person/search?';
         if(document.getElementById('fullName').value != ''){
         url += 'fullName='+document.getElementById('fullName').value;
      }
      if(document.getElementById('idGender').value != ''){
         url += '&idGender='+document.getElementById('idGender').value;
      }
      if(document.getElementById('birthdate').value != ''){
         birthdate = document.getElementById('birthdate').value;
         birthdateFormatted = birthdate + 'T00:00:00';
         url += '&birthdate='+birthdateFormatted;
      }
   }

               fetch(`${url}`, {
                   method: 'GET',
                   headers: {
                       'Content-Type': 'application/json'
                   },
               }).then(response => response.json())
                     .then(data => {

                     birthdateResponse = data != null && data.birthdate != null ? data.birthdate.slice(0, 10) : '';

                     document.querySelector('#personId').textContent = data.id;
                     document.querySelector('#personName').textContent = data.fullName;
                     document.querySelector('#personBirthdate').textContent = birthdateResponse;
                     document.querySelector('#personGender').textContent = data.gender;
                     document.querySelector('#sucessUpdate').classList.value = '';
                     document.querySelector('#sucessUpdate').classList.add('updateSucess');

                     console.log("Search Person successfully!");
                   }).catch(error => {
                   console.log("Error search person.");
                   console.error('Error:', error);
               });
}

 function enviaForm(tokenP, event) {
            let id = '';
            let fullName = '';
            let birthdate = '';
            let idGender = '';
            let birthdateFormatted = '';
            let birthdateResponse = '';
            let elementoClassHidden = document.querySelector('hiddenSucess');
            let path = '';
            let methodForm = '';
            var token = tokenP;

            if(document.querySelector("#personForm") != null){
                    if(document.getElementById('inputUpdateIdData') != null){
                        id = document.getElementById('inputUpdateIdData').value;
                        fullName = document.getElementById('fullName').value;
                        if(document.getElementById('birthdate').value != ''){
                            birthdate = document.getElementById('birthdate').value;
                            birthdateFormatted = birthdate + 'T00:00:00';
                        }
                        idGender = parseInt(document.getElementById('idGender').value);
                        path ='/person/update/'+id;
                        methodForm = 'PATCH';
                    }else if(document.getElementById('inputRemoveIdData') != null){
                        id = document.getElementById('inputRemoveIdData').value;
                        path ='/person/remove/'+id;
                        methodForm = 'DELETE';
                    }

            }else if(document.querySelector("#personFormCreate") != null){
                fullName = document.getElementById('fullName').value;
                birthdate = document.getElementById('birthdate').value;
                idGender = parseInt(document.getElementById('idGender').value);
                birthdateFormatted = birthdate + 'T00:00:00';
                path ='/person/create';
                methodForm = 'POST';
            }

            const personDTO = {
                fullName: fullName,
                birthdate: birthdateFormatted,
                idGender: idGender
            };
            if(token != null){
            fetch(`${path}`, {
                method: methodForm,
                headers: {
                    'Content-Type': 'application/json',
                    'Autorization': `${token}`
                },
                body: JSON.stringify(personDTO)
            }).then(response => response.json())
                  .then(data => {

                  birthdateResponse = data != null && data.birthdate != null ? data.birthdate.slice(0, 10) : '';

                  document.querySelector('#personId').textContent = data.id;
                  document.querySelector('#personName').textContent = data.fullName;
                  document.querySelector('#personBirthdate').textContent = birthdateResponse;
                  document.querySelector('#personGender').textContent = data.gender;
                  document.querySelector('#sucessUpdate').classList.value = '';
                  document.querySelector('#sucessUpdate').classList.add('updateSucess');

                  console.log("successfully!");
                }).catch(error => {
                console.log("Error person.");
                console.error('Error:', error);
            });
            }else{
                console.log("Não possivél buscar um token.");
            }
           }

        function submitForm(event){
            event.preventDefault();
            path ='/auth/login';
            methodForm = 'POST';

            const AuthenticationDTO = {
                   login: 'system@system.com',
                   password: 'system2024'
            };

              fetch(`${path}`, {
                  method: methodForm,
                  headers: {
                      'Content-Type': 'application/json'
                  },
                  body: JSON.stringify(AuthenticationDTO)
              }).then(response => response.json())
                    .then(loginResponse => {
                    console.log("successfully!");
                    enviaForm(loginResponse.token, event);
                  }).catch(error => {
                  console.log("Error person.");
                  console.error('Error:', error);
              });
        }