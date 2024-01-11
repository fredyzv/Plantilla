Paginador = function(divPaginador, tabla)
{
    this.miDiv = divPaginador; //un DIV donde irán controles de paginación
    this.tabla = tabla;           //la tabla a paginar
    this.tamPagina = 5; //el tamaño de la página (filas por página)
    this.pagActual = 1;         //asumiendo que se parte en página 1
    this.paginas = Math.floor((this.tabla.rows.length - 1) / this.tamPagina); //¿?

    this.SetPagina = function(num)
    {
        if (num < 0 || num > this.paginas)
            return;

        this.pagActual = num;
        var min = 1 + (this.pagActual - 1) * this.tamPagina;
        var max = min + this.tamPagina - 1;

        for(var i = 1; i < this.tabla.rows.length; i++)
        {
            if (i < min || i > max)
                this.tabla.rows[i].style.display = 'none';
            else
                this.tabla.rows[i].style.display = '';
        }
        this.miDiv.firstChild.rows[0].cells[1].innerHTML = this.pagActual;
    }

    this.Mostrar = function()
    {
        //Crear la tabla
        var tblPaginador = document.createElement('table');

        //Agregar una fila a la tabla
        var fil = tblPaginador.insertRow(tblPaginador.rows.length);

        //Ahora, agregar las celdas que serán los controles
        var ant = fil.insertCell(fil.cells.length);
        ant.innerHTML = '<';
        ant.className = 'prev'; //con eso le asigno un estilo y una clase al boton
        var self = this;
        ant.onclick = function()
        {
            if (self.pagActual == 1)
                return;
            self.SetPagina(self.pagActual - 1);
        }

        var num = fil.insertCell(fil.cells.length);
        num.innerHTML = ''; //en teoria, aún no se el número de la página
        num.className = 'num ';

        var sig = fil.insertCell(fil.cells.length);
        sig.innerHTML = '>';
        sig.className = 'next';
        sig.onclick = function()
        {
            if (self.pagActual == self.paginas)
                return;
            self.SetPagina(self.pagActual + 1);
        }

        //Como ya tengo mi tabla, puedo agregarla al DIV de los controles
        this.miDiv.appendChild(tblPaginador);


        if (this.tabla.rows.length - 1 > this.paginas * this.tamPagina)
            this.paginas = this.paginas + 1;

        this.SetPagina(this.pagActual);
    }
}

var p = new Paginador(
    document.getElementById('paginador'),
    document.getElementById('table'),
    4
);

//Search all columns
var searchBox = document.getElementById('searchBox');
searchBox.addEventListener('keyup', function () {
    var keyword = this.value;
    keyword = keyword.toUpperCase();
    var table = document.getElementById('table');
    var all_tr = table.getElementsByTagName('tr');
    for (var i = 0; i < all_tr.length; i++) {
        var all_columns = all_tr[i].getElementsByTagName('td');
        for (var j = 0; j < all_columns.length; j++) {
            if (all_columns[j]) {
                var column_value = all_columns[j].innerText || all_columns[j].textContent;
                column_value = column_value.toUpperCase();
                if (column_value.indexOf(keyword) > -1) {
                    all_tr[i].style.display = '';
                    break;
                } else {
                    all_tr[i].style.display = 'none';

                }
            }
        }

    }

})
/*
//Primera columna
const searchInput = document.getElementById('searchs');
const rows = document.querySelectorAll('tbody tr');
//console.log(rows);
searchInput.addEventListener('keyup', function (event) {
    //console.log(event);
    const q = event.target.value.toLowerCase();
    rows.forEach((row) => {
        row.querySelector('th').textContent.toLowerCase().startsWith(q)
            ? (row.style.display = 'table-row')
            : (row.style.display = 'none');
    });

});
*/
p.Mostrar();