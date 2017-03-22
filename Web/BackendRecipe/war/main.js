function cambiar(esto) {
	var vista=document.getElementById(esto).style.display;
	if(vista=='none') vista='block';
	else vista='none';
	document.getElementById(esto).style.display=vista;
}

function ingrediente_instantaneo()
{
 var ingre= document.getElementById("nameIng").value;
 var descri= document.getElementById("desIng").value;
 var table = document.getElementById("mitabla");          
 var row = table.insertRow(1);
 var cell1 = row.insertCell(0);
 var cell2 = row.insertCell(1);
 cell1.innerHTML = ingre;
 cell2.innerHTML = descri;
}

//FUNCION INICIAL

var id_sesion, recetario, ingredientes, usuario_email;
function sesion(){
  id_sesion = JSON.parse(sessionStorage.getItem("idRESP"));
  recetario = JSON.parse(sessionStorage.getItem("recetasRESP"));
  usuario_email = sessionStorage.getItem("emailRESP");
  ingredientes = JSON.parse(sessionStorage.getItem("LingredientesRESP"));
  lista_ingre();
    //console.log(id_sesion + " " + usuario_email);
    //console.log(recetario);
    //console.log(ingredientes);
    $("#SesionUsuario").text(usuario_email);

  }

//AÑADIR Y CONSEGUIR USUARIOS (LOGIN Y REGISTRO)

function add_a_user(){
	console.log ("add_a_user is starting");
  var user =
  {
   id: document.getElementById("useridR").value,
   email: document.getElementById("usermailR").value,		
 };
 addUser(user);
 console.log ("add_a_user has ended");
}


function getUser_response(resp){
  /* It processes the response after calling getUser API method */
  if (!resp.code){
    sessionStorage.setItem("emailRESP", resp.email.email);
    sessionStorage.setItem("idRESP", resp.id);
    sessionStorage.setItem("recetasRESP", JSON.stringify(resp.recipes));
    
    console.log(resp);
    //console.log(window.location.href);
    if(window.location.href=="http://localhost:8888/login.html") window.location.assign("./index.html");
  }    
}

function get_a_user(){
	console.log ("get_a_user is starting");
  getUser(document.getElementById("useridL").value);
  console.log ("get_a_user has ended");
}

function get_a_userID(){
  console.log ("get_a_userID is starting");
  getUser(id_sesion);
  console.log ("get_a_userID has ended");
}

//AÑADIR RECETAS E INGREDIENTES

function add_a_recipe(){
  console.log ("add_a_recipe is starting");
  var addrecipe_userid = id_sesion;
  var recipe = {
    id_recipe: document.getElementById("idR").value,
    card: {
      name: document.getElementById("nombreR").value,
      type: "0",
      summary: document.getElementById("DescripcionR").value,
    },
    steps: document.getElementById("pasosR").value,
  };
  addRecipe(addrecipe_userid, recipe);
  console.log ("add_a_recipe has ended");
}

function add_a_ingredient(){
  console.log ("add_a_ingredient is starting");
  var ingredient =
  {
    id_ingredient: document.getElementById("idIng").value,
    name: document.getElementById("nameIng").value,
    nutritionaldesc: document.getElementById("desIng").value,
  };
  addIngredient(ingredient);
  console.log ("add_a_ingredient has ended");
}


//MOSTRAR RECETAS

var nameRec, descriRec, pasosRec;
function getRecipe_response(resp){
  /* It processes the response after calling getRecipe API method */
  if (!resp.code){
    nameRec=resp.card.name;
    descriRec=resp.card.summary;
    pasosRec=resp.steps;
    console.log(resp);
  }
}

function get_a_recipe(id){
  console.log("get_a_recipe is starting");
  var iduser = id_sesion;
  var idrecipe = id;
  getRecipe(iduser, idrecipe);
  console.log("get_a_recipe has ended");
}

var id_ingre;
function get_recipiente(){
  var i=0;
  var m=1;
  var long=recetario.length;
  var $RecetasContainer = $('#recetario');
  while(i<long){
    var article = template
    .replace(/:x:/g,recetario[i].id_recipe)
    .replace(":y:",m)
    .replace(":nombre:",recetario[i].card.name)
    .replace(":descripcion:",recetario[i].card.summary)
    .replace(":pasos:",recetario[i].steps);
    $RecetasContainer.append($(article));
    //console.log(recetario[i]);
    var receta=("medidas"+recetario[i].id_recipe);
    var j=0;
    var long2 = recetario[i].ling.length;
    while(j<long2){
      var idi=recetario[i].ling[j].id_ing;
      var k=0;
      var encontrado=false;
      //console.log(recetario[i].ling[j].id_ing);
      while (!encontrado && k<ingredientes.length){
        if(idi==ingredientes[k].id_ingredient) encontrado=true;
        else k++;
      }
      var $IngredientesContainer = $('#'+receta);
      var article2 = plantilla_ingrediente
      .replace(":ingrediente:", ingredientes[k].name)
      .replace(":cantidad:", recetario[i].ling[j].quantity)
      .replace(":unidad:", recetario[i].ling[j].measurementu);
      $IngredientesContainer.append($(article2));
      j++;
    }
    if (m==4) m=1;
    else m++;
    i++;
  }
}

//OBTENER UN INGREDIENTE

function get_an_ingredient(id){
  console.log("get_an_ingredient is starting");
  getIngredient(id);
  console.log("get_an_ingredient has ended");
}

function getIngredient_response(resp){
  if (!resp.code){
    console.log(resp);
  }
}

//LISTA DE INGREDIENTES

function listIngredient_response(resp){
  if (!resp.code){
    sessionStorage.setItem("LingredientesRESP", JSON.stringify(resp.items));
  }
}

function list_ingredients(){
  console.log("list_ingredients is starting");
  listIngredient();
  console.log("list_ingredients has ended");
}


function lista_ingre(){
  var i=0;
  //ingredientes= JSON.parse(sessionStorage.getItem("LingredientesRESP"));
  var long=ingredientes.length;
  var $ListaIngContainer = $('#ingredienteR');

  while(i<long){
    var article = plantilla_listaING
    .replace(":name:",ingredientes[i].name+ " ("+ ingredientes[i].id_ingredient+")");  

    $ListaIngContainer.append($(article));
    console.log(ingredientes[i]);
    i++;
  }
}
//AÑADIR INGREDIENTE A UNA RECETA

function add_an_ingredient_to_recipe(){
  console.log("add_an_ingredient_to_recipe is starting");
  var iduser = id_sesion;
  var idrecipe = añadirING;
  var ch = document.getElementById("ingredienteR").value
  ch=ch.replace(/[a-z]/g, "");
  ch=ch.replace(/[A-Z]/g, "");
  ch=ch.replace("(", "");
  ch=ch.replace(")", "");
  ch=ch.replace(/ /g,"");
  console.log("id_ingre: "+ch);

  var idingredient = ch;
  var recipeingredient = {
    id_ingrrecipe: ch,
    quantity: document.getElementById("cantidadR").value,
    measurementu: document.getElementById("unidadR").value,
    id_ing: idingredient,
  };
  addSpecificIngredientToRecipe(idrecipe, iduser, idingredient, recipeingredient);
  console.log("add_an_ingredient_to_recipe has ended");
}

//MOSTRAR INGREDIENTES EN LA PESTAÑA INGREDIENTES



function mostrar_ingredientes(){
  var i=0;
  var long=ingredientes.length;
  var $ingredientesContainer = $('#listaingrediente');
  while(i<long){
    var article3 = plantilla_ingreLista
    .replace(":name:", ingredientes[i].name)
    .replace(":descripcion:", ingredientes[i].nutritionaldesc);

    $ingredientesContainer.append($(article3));
    i++;
  }
}




//MODIFICAR UN USUARIO

function update_user_maindata(){
  console.log("update_user_maindata is starting");
  var userid = id_sesion;
  var useremail = document.getElementById("emailPerfil").value;
  var theuser = {
    id: userid,
    email: useremail,
  };
  updateUserMainData(theuser);
  sessionStorage.setItem("emailRESP", useremail);
  usuario_email=useremail;
  $("#Perfil_nombre").text(usuario_email);
  console.log("update_user_maindata has ended");
}

function perfil(){
  $("#Perfil_nombre").text(usuario_email);
  $("#Perfil_id").text(id_sesion);
}


var añadirING; //VARIABLE PARA SABER EL ID DE LA RECETA QUE QUIERE AÑADIR UN INGREDIENTE

  //PLANTILLAS DE RECETAS E INGREDIENTES

var template='<div class="container-fluid">'+
          '<div class="col-md-3"></div>'+
                '<div style="margin-bottom: 3%" class="col-md-9">'+
                  '<div class="demo-card-wide mdl-card mdl-shadow--2dp">'+
                    '<div class="mdl-card__title background_card:y:">'+
                      '<h1 class="mdl-card__title-text">:nombre:</h1>'+
                    '</div>'+
                    '<div style="font-size: 1em;" class="mdl-card__supporting-text">'+
                      ':descripcion:'+
                    '</div>'+

                    '<div  class="mdl-card__actions mdl-card--border"  id="pasos:x:" style="display: none;">'+
                       '<div style="font-size: 1em;" class="mdl-card__supporting-text">'+
                          ':pasos:'+     
                       '</div>'+
                    '</div>'+
                    '<script>'+
                    'function saberING:x:(){'+
                      'añadirING=:x:;'+
                    '}'+
                     'function cambiarPasos:x:(){'+
                        'var vista=document.getElementById("pasos:x:").style.display;'+
                        'if(vista=="none") vista="block";'+
                        'else vista="none";'+
                        'document.getElementById("pasos:x:").style.display=vista;'+
                      '}'+
                      'function cambiarIngredientes:x:(){'+
                        'var vista=document.getElementById("ingredientes:x:").style.display;'+
                        'if(vista=="none") vista="block";'+
                        'else vista="none";'+
                        'document.getElementById("ingredientes:x:").style.display=vista;'+
                      '}'+

                    '</script>'+
                  '<div class="ingredientes_centrar" id="ingredientes:x:" style="display: none;">'+
                      '<table class="mdl-data-table mdl-js-data-table mdl-data-table mdl-shadow--2dp">'+
                      '<thead>'+
                        '<tr>'+
                          '<th class="mdl-data-table__cell--non-numeric mdl-color-text--blue-400"> Ingrediente</th>'+
                          '<th class="mdl-color-text--blue-400">Cantidad</th>'+
                          '<th class="mdl-color-text--blue-400">Unidad</th>'+                   
                        '</tr>'+
                      '</thead>'+
                      '<tbody id="medidas:x:">'+

                      '</tbody>'+                      
                    '</table>'+
                    '<button class="mdl-button mdl-js-button mdl-button--primary mdl-color-text--blue-400 " onclick="saberING:x:()" style="float: right; margin-top:15px; margin-bottom: -10px; margin-right: 20px;" data-toggle="modal" data-target="#myModal2">'+
                       'Añadir Ingrediente'+
                    '</button>'+                   
                 '</div>'+

                    '<div class="mdl-card__actions mdl-card--border">'+
                      '<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">'+
                        '<i class="material-icons">favorite</i>'+
                      '</button>'+                   

                      '<button class="mdl-button mdl-js-button mdl-button--primary"  onclick="cambiarPasos:x:(); return false;" style="float: right;">'+
                        'Pasos'+
                      '</button>'+
                      '<button class="mdl-button mdl-js-button mdl-button--primary" onclick="cambiarIngredientes:x:(); return false;" style="float: right;">'+
                        'Ingredientes'+
                      '</button>'+
                    '</div>'+
                    '<div class="mdl-card__menu">'+
                      '<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">'+
                        '<i class="material-icons">share</i>'+
                      '</button>'+
                      '</div>'+
                      '</div>'+
                    '</div>';            

var plantilla_listaING='<option>:name:</option>';

var plantilla_ingreLista= '<tr>'+
                  '<td class="mdl-data-table__cell--non-numeric">:name:</td>'+
                  '<td class="mdl-color-text--grey-600">:descripcion:</td>'+
                '</tr>';

var plantilla_ingrediente ='<tr>'+
                '<td class="mdl-data-table__cell--non-numeric" >:ingrediente:</td>'+
                '<td>:cantidad:</td>'+
                '<td>:unidad:</td>'+
              '</tr>';