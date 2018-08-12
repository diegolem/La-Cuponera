(function () {

    $.extend(true, $.fn.dataTable.defaults, {
        "language": {
            "sProcessing": "Procesando...",
            "sLengthMenu": "",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "Ningún dato disponible en esta tabla",
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix": "",
            "sSearch": "Buscar:",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        "dom": '<"toolbar">frtip',
        "initComplete": function(settings, json) {
            $($.fn.dataTableExt.oStdClasses.sPageButton = "button").addClass("btn");
        }
    });

    $.validator.setDefaults({
        errorClass: 'invalid',
        validClass: '_none',
        errorPlacement: function(error, element) {
            $(element).parent().find('span.helper-text').remove();
            $(element).parent()
                .append(`<span class='helper-text' data-error='${error.text()}'></span>`);
        }
    });

    $(document).ready(function () {

        M.AutoInit();

        if($('.datepicker').length > 0){
            var elem = document.querySelector('.datepicker');
            var instance = M.Datepicker.init(elem, {
                autoClose: true,
                format: 'yyyy-mm-dd',
                firstDay: 1,
                minDate: new Date('1950-01-01'),
                maxDate: new Date(),
                showClearBtn: true,

                i18n: {
                    clear: 'Limpiar',
                    close: 'Cerrar',
                    cancel: 'Cancelar',
                    previousMonth: '<',
                    nextMonth: '>',
        
                    months: [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre' ],
                    monthsShort: [ 'ene', 'feb', 'mar', 'abr', 'may', 'jun', 'jul', 'ago', 'sep', 'oct', 'nov', 'dic' ],
                    weekdays: [ 'Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado' ],
                    weekdaysShort: [ 'dom', 'lun', 'mar', 'mié', 'jue', 'vie', 'sáb' ],
                    weekdaysAbbrev:	['D','L','M','X','J','V','S']
                }
            });
        }

        if($('.slider').length > 0){
            $('.slider').slider();
        }

        if($('.dropdown-trigger').length > 0){
            $('.dropdown-trigger').dropdown();
        }

        if($('.sidenav').length > 0){
            $('.sidenav').sidenav();
        }

        if($('.modal').length > 0){
            $('.modal').modal();
        }

        if($('#dropUser').length > 0){
            $("[data-target='dropUser']").dropdown({
                alignment: 'left',
                constrainWidth: false,
                coverTrigger: false
            });
        }

        if($('table.tbl').length > 0){
            $('table.tbl').DataTable();
        }
    });
})
();

(function (c) {
    "function" === typeof define && define.amd ? define(["jquery", "datatables.net"], function (a) {
        return c(a, window, document)
    }) : "object" === typeof exports ? module.exports = function (a, d) {
        a || (a = window);
        if (!d || !d.fn.dataTable) d = require("datatables.net")(a, d).$;
        return c(d, a, a.document)
    } : c(jQuery, window, document)
})(function (c, a, d, r) {
    var g = c.fn.dataTable;
    c.extend(!0, g.defaults, {
        dom: "<'mdl-grid'<'mdl-cell mdl-cell--6-col'l><'mdl-cell mdl-cell--6-col'f>><'mdl-grid dt-table'<'mdl-cell mdl-cell--12-col'tr>><'mdl-grid'<'mdl-cell mdl-cell--4-col'i><'mdl-cell mdl-cell--8-col'p>>",
        renderer: "material"
    });
    c.extend(g.ext.classes, {
        sWrapper: "dataTables_wrapper form-inline dt-material",
        sFilterInput: "form-control input-sm",
        sLengthSelect: "form-control input-sm",
        sProcessing: "dataTables_processing panel panel-default"
    });
    g.ext.renderer.pageButton.material = function (a, h, s, t, i, n) {
        var o = new g.Api(a),
            l = a.oLanguage.oPaginate,
            u = a.oLanguage.oAria.paginate || {},
            f, e, p = 0,
            q = function (d, g) {
                var m, h, j, b, k = function (a) {
                    a.preventDefault();
                    !c(a.currentTarget).hasClass("disabled") && o.page() != a.data.action &&
                    o.page(a.data.action).draw("page")
                };
                m = 0;
                for (h = g.length; m < h; m++)
                    if (b = g[m], c.isArray(b)) q(d, b);
                    else {
                        f = "";
                        j = !1;
                        switch (b) {
                            case "ellipsis":
                                f = "&#x2026;";
                                e = "disabled";
                                break;
                            case "first":
                                f = l.sFirst;
                                e = b + (0 < i ? "" : " disabled");
                                break;
                            case "previous":
                                f = l.sPrevious;
                                e = b + (0 < i ? "" : " disabled");
                                break;
                            case "next":
                                f = l.sNext;
                                e = b + (i < n - 1 ? "" : " disabled");
                                break;
                            case "last":
                                f = l.sLast;
                                e = b + (i < n - 1 ? "" : " disabled");
                                break;
                            default:
                                f = b + 1, e = "", j = i === b
                        }
                        j && (e += " slctd");
                        f && (j = c("<button>", {
                            "class": "btn " +
                            e,
                            id: 0 === s && "string" === typeof b ? a.sTableId + "_" + b : null,
                            "aria-controls": a.sTableId,
                            "aria-label": u[b],
                            "data-dt-idx": p,
                            tabindex: a.iTabIndex,
                            disabled: -1 !== e.indexOf("disabled")
                        }).html(f).appendTo(d), a.oApi._fnBindAction(j, {
                            action: b
                        }, k), p++)
                    }
            },
            k;
        try {
            k = c(h).find(d.activeElement).data("dt-idx")
        } catch (v) { }
        q(c(h).empty().html('<div class="pagination"/>').children(), t);
        k !== r && c(h).find("[data-dt-idx=" + k + "]").focus()
    };
    return g
});