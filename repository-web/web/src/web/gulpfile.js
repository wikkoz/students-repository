// 1. LIBRARIES
// - - - - - - - - - - - - - - -
var gulp                 = require('gulp');
var addStream            = require('add-stream');
var autoprefixer         = require('gulp-autoprefixer');
var concat               = require('gulp-concat');
var cssmin               = require('gulp-cssmin');
var environments         = require('gulp-environments');
var less                 = require('gulp-less');
var ngAnnotate           = require('gulp-ng-annotate');
var path                 = require('path');
var plumber              = require('gulp-plumber');
var rimraf               = require('rimraf');
var rename               = require('gulp-rename');
var removeCode           = require('gulp-remove-code');
var sequence             = require('run-sequence');
var uglify               = require('gulp-uglify');

// 2. VARIABLES
// - - - - - - - - - - - - - - - 
var lib = {
	scripts: [
		'bower_components/jquery/dist/jquery.js',
		'bower_components/bootstrap/dist/js/bootstrap.js',
		'bower_components/angular/angular.js',
		'bower_components/angular-ui-router/release/angular-ui-router.js',
		'bower_components/angular-animate/angular-animate.js',
		'bower_components/angular-touch/angular-touch.js',
		'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
		'bower_components/angular-file-upload/dist/angular-file-upload.js',
		'bower_components/lodash/lodash.js',
		'bower_components/angular-resource/angular-resource.js',
		'bower_components/angular-base64-upload/dist/angular-base64-upload.js'
	],
	styles: [
		'bower_components/angular-ui-select/dist/select.css',
		'bower_components/angular-ui-notification/dist/angular-ui-notification.css',
		'bower_components/angular-aside/dist/css/angular-aside.css'
	],
	fonts: [
		'bower_components/**/*.{ttf,woff,woff2,eof,svg}'
	]
};

var app = {
	scripts: ['app/**/*.js', '!app/**/*.spec.js'],
	script_tests: ['app/**/*.spec.js'],
	templates: ['app/components/**/*.html'],
	index_dev: ['app/index.html'],
	dst: '../../../src/main/resources/static',
	dst2: '../../../target/classes/static'
};

var assets = {
	index: ['less/index.less'],
	less: ['less/templates'],
	regex: ['less/templates/*.less']
};

var development = environments.development;
var production = environments.production;

// Gulp plumber error handler
var onError = function(err) {
	console.log(err);
	this.emit('end');
};

// 3. TASKS
// - - - - - - - - - - - - - - - -
gulp.task('clean', function (cb) {
	return rimraf(app.dst, cb);
});

gulp.task('copy-lib-js', function () {
	return gulp.src(lib.scripts)
		.pipe(plumber({
			errorHandler: onError
		}))
		.pipe(concat('lib.js'))
		.pipe(gulp.dest(app.dst + '/lib/js/'));
});

gulp.task('copy-fonts', function () {
	return gulp.src(lib.fonts)
		.pipe(rename({
			dirname: '/fonts'
		}))
		.pipe(gulp.dest(app.dst + '/css/'))
		.pipe(gulp.dest(app.dst + '/lib/'))
		.pipe(gulp.dest(app.dst))
		.pipe(gulp.dest(app.dst2 + '/css/'))
		.pipe(gulp.dest(app.dst2 + '/lib/'))
		.pipe(gulp.dest(app.dst2));
});

gulp.task('copy-lib-styles', function() {
	return gulp.src(lib.styles)
		.pipe(concat('lib.css'))
		.pipe(gulp.dest(app.dst + '/lib/css'))
		.pipe(gulp.dest(app.dst2 + '/lib/css'));
});

gulp.task('combine-js-html', function() {
	return gulp.src(
			lib.scripts
			.concat(app.scripts))
		.pipe(plumber({
			errorHandler: onError
		}))
		.pipe(addStream.obj(prepareTemplates()))
		.pipe(concat('scripts.js'))
		.pipe(ngAnnotate())
		.pipe(uglify())
		.pipe(gulp.dest(app.dst))
		.pipe(gulp.dest(app.dst2));
});

gulp.task('copy-compile-less', function () {
	return gulp.src(assets.index)
		.pipe(plumber({
			errorHandler: onError
		}))
		.pipe(less({
			paths: [
				__dirname,
				'bower_components/bootstrap/less',
				'less/templates'
			]
		}))
		.pipe(autoprefixer({
			browsers: ['> 5%'],
			cascade: false
		}))
		.pipe(production(cssmin()))
		.pipe(gulp.dest(app.dst + '/css'))
		.pipe(gulp.dest(app.dst2 + '/css'));
});

gulp.task('copy-dev-index', function () {
	gulp.src(app.index_dev, {
			base: 'app'
		})
		.pipe(concat('index.html'))
		.pipe(development(removeCode({
			development: true
			})))
		.pipe(production(removeCode({
			production: true
			})))
		.pipe(gulp.dest(app.dst))
		.pipe(gulp.dest(app.dst2));
});

gulp.task('copy-pages', function () {
	return gulp.src(app.templates, {
			base: 'app'
		})
		.pipe(gulp.dest(app.dst))
		.pipe(gulp.dest(app.dst2));
});

gulp.task('copy-scripts', function () {
	return gulp.src(app.scripts, {
			base: 'app'
		})
		.pipe(ngAnnotate())
		.pipe(gulp.dest(app.dst))
		.pipe(gulp.dest(app.dst2));
});

gulp.task('build', function () {
	return sequence(
		'clean',
		['copy-compile-less',
		'copy-lib-js',
		'copy-fonts',
		'copy-lib-styles',
		'copy-scripts',
		'copy-pages'],
		'copy-dev-index',
		function () { 
			console.log("Successfully build version."); 
		});
});

gulp.task('watch', function () {
	gulp.watch(app.index_dev, ['copy-dev-index']);
	gulp.watch(app.scripts, ['copy-scripts']);
	gulp.watch(assets.regex, ['copy-compile-less']);
	gulp.watch(assets.regex, ['copy-lib-styles']);
	return gulp.watch(app.templates, ['copy-pages']);
});

