class Loader {
    constructor() {
        if ($('.loader_cont').length == 0) {
            var container = document.createElement('div');
            container.className = 'loader_cont';

            var preloader_wrapper = document.createElement('div');
            preloader_wrapper.className = 'preloader-wrapper big active';

            var spinner_layer = document.createElement('div');
            spinner_layer.className = 'spinner-layer spinner-blue-only';

            var circle_clipper_l = document.createElement('div');
            circle_clipper_l.className = 'circle-clipper left';

            var circle_clipper_r = document.createElement('div');
            circle_clipper_r.className = 'circle-clipper right';

            var gap = document.createElement('div');
            gap.className = 'gap-patch';

            var circle = document.createElement('div');
            circle.className = 'circle';
            circle_clipper_l.append(circle);

            circle = document.createElement('div');
            circle.className = 'circle';
            circle_clipper_r.append(circle);

            circle = document.createElement('div');
            circle.className = 'circle';
            gap.append(circle)

            spinner_layer.append(circle_clipper_l);
            spinner_layer.append(gap);
            spinner_layer.append(circle_clipper_r);
            preloader_wrapper.append(spinner_layer);
            container.append(preloader_wrapper);
            container.style.position = 'fixed';
            container.style.top = '-100px';
            container.style.left = '0';
            container.style.bottom = '0';
            container.style.right = '0';
            container.style.height = '125%';
            container.style.width = '100%';
            container.style.background = 'rgba(0, 0, 0, 0.6)';
            container.style.zIndex = '100001';
            container.style.display = 'none';
            container.style.justifyContent = 'center';
            container.style.alignItems = 'center';

            $('body').append(container);
        }
        this.obj = $('.loader_cont');
    }

    in(){
        this.obj.fadeIn('slow');
        this.obj.css('display', 'flex');
    }

    out(){
        this.obj.fadeOut('slow');
    }
}