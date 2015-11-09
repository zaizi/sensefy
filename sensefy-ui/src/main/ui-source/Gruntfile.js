/*
 #   by Sameera @ zaizi.com
 #   for Sensefy Search
 #   manage CSS and JS lint
 #   local server to preview
 #   build the files
 */
'use strict';
// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// use this if you want to recursively match all subfolders:
// 'test/spec/**/*.js'
var modRewrite = require('connect-modrewrite');
module.exports = function (grunt) {
    // Load grunt tasks automatically
    require('load-grunt-tasks')(grunt);
    // Time how long tasks take. Can help when optimizing build times
    require('time-grunt')(grunt);
    // Define the configuration for all the tasks
    grunt
        .initConfig({
            // Project settings
            yeoman: {
                // configurable paths
                app: require('./bower.json').appPath || 'app',
                dist: 'dist'
            },
            // Watches files for changes and runs tasks based on the changed
            // files
            watch: {
                bower: {
                    files: ['bower.json'],
                    tasks: ['bowerInstall']
                },
                scripts: {
                    files: ['<%= yeoman.app %>/scripts/{,*/}*.js'],
                    tasks: ['newer:copy:scripts']
                },
                styles: {
                    files: ['<%= yeoman.app %>/styles/{,*/}*.css'],
                    tasks: ['newer:copy:styles', 'autoprefixer']
                },
                gruntfile: {
                    files: ['Gruntfile.js']
                },
                livereload: {
                    options: {
                        livereload: '<%= connect.options.livereload %>'
                    },
                    files: ['<%= yeoman.app %>/{,*/}*.html',
                        '<%= yeoman.app %>/assets/styles/{,*/}*.css',
                        '<%= yeoman.app %>/assets/scripts/{,*/}*.js',
                        '<%= yeoman.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}']
                }
            },
            // The actual grunt server settings
            connect: {
                options: {
                    port: 9010,
                    // Change this to '0.0.0.0' to access the server from
                    // outside.
                    hostname: 'localhost',
                    livereload: 35729
                },
                livereload: {
                    options: {
                        open: true,
                        base: ['app/assets', '<%= yeoman.app %>',
                            '<%= yeoman.app %>/../'],
                        middleware: function (connect, options) {
                            var middlewares = [];
                            middlewares
                                .push(modRewrite(['!/api|/assets|\\.png|\\.jpg|\\.gif|\\.html|\\.js|\\.css|\\woff|\\ttf|\\swf$ /index.html']));
                            options.base.forEach(function (base) {
                                // Serve static files.
                                middlewares.push(connect.static(base));
                            });
                            // middlewares.push(proxySnippet);
                            return middlewares;
                        }
                    }
                },
                test: {
                    options: {
                        port: 9007,
                        base: ['app/assets', 'test', '<%= yeoman.app %>']
                    }
                },
                dist: {
                    options: {
                        base: '<%= yeoman.dist %>'
                    }
                }
            },
            // Make sure code styles are up to par and there are no obvious
            // mistakes
            jshint: {
                options: {
                    curly: true,
                    eqeqeq: true,
                    // immed: true,
                    // latedef: true,
                    // newcap: true,
                    // noarg: true,
                    // sub: true,
                    // undef: true,
                    // boss: true,
                    eqnull: true,
                    // node: true,
                    browser: true,
                    globals: {
                        jQuery: true
                    },
                    ignores: ['<%= yeoman.app %>/assets/scripts/detect.js']
                },
                ignore_warning: {
                    options: {
                        '-W030': true
                    }
                }
            },
            // Make sure code styles are up to par and there are no obvious
            // mistakes
            csslint: {
                options: {
                    //csslintrc : '.csslintrc'
                },
                strict: {
                    src: ['<%= yeoman.app %>/styles//*.css']
                },
                lax: {
                    options: {
                        import: false
                    },
                    src: ['<%= yeoman.app %>/styles/*.css']
                }
            },
            // Empties folders to start fresh
            clean: {
                dist: {
                    files: [{
                        dot: true,
                        src: ['<%= yeoman.dist %>/*',
                            '!<%= yeoman.dist %>/.git*']
                    }]
                },
                server: '<%= yeoman.app %>/assets'
            },
            // Add vendor prefixed styles
            autoprefixer: {
                options: {
                    browsers: ['last 1 version']
                },
                dist: {
                    files: [{
                        expand: true,
                        cwd: '<%= yeoman.app %>/assets/styles/',
                        src: '{,*/}*.css',
                        dest: '<%= yeoman.app %>/assets/styles/',
                        ext: '.css'
                    }]
                }
            },
            // Automatically inject Bower components into the app
            bowerInstall: {
                app: {
                    src: ['<%= yeoman.app %>/index.html'],
                    ignorePath: '<%= yeoman.app %>/'
                }
            },
            // Compiles CoffeeScript to JavaScript
            javascripts: {
                options: {
                    sourceMap: true,
                    sourceRoot: ''
                },
                dist: {
                    files: [{
                        expand: true,
                        cwd: '<%= yeoman.app %>/scripts',
                        src: '**/*.js',
                        dest: '<%= yeoman.app %>/assets/scripts',
                        ext: '.js'
                    }]
                },
                test: {
                    files: [{
                        expand: true,
                        cwd: 'test/spec',
                        src: '{,*/}*.js',
                        dest: 'app/assets/spec',
                        ext: '.js'
                    }]
                }
            },
            // Renames files for browser caching purposes
            rev: {
                dist: {
                    files: {
                        src: [
                            '<%= yeoman.dist %>/scripts/{,*/}*.js',
                            '<%= yeoman.dist %>/styles/{,*/}*.css',
                            '<%= yeoman.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
                            '<%= yeoman.dist %>/styles/fonts/*']
                    }
                }
            },
            // Reads HTML for usemin blocks to enable smart builds that
            // automatically
            // concat, minify and revision files. Creates configurations in
            // memory so
            // additional tasks can operate on them
            useminPrepare: {
                html: '<%= yeoman.app %>/index.html',
                options: {
                    dest: '<%= yeoman.dist %>',
                    flow: {
                        html: {
                            steps: {
                                js: ['concat', 'uglifyjs'],
                                css: ['cssmin']
                            },
                            post: {}
                        }
                    }
                }
            },
            // Performs rewrites based on rev and the useminPrepare
            // configuration
            usemin: {
                html: ['<%= yeoman.dist %>/{,*/,**/}*.html'],
                css: ['<%= yeoman.dist %>/styles/{,*/}*.css'],
                options: {
                    assetsDirs: ['<%= yeoman.dist %>']
                }
            },
            imagemin: {
                dist: {
                    files: [{
                        expand: true,
                        cwd: '<%= yeoman.app %>/images',
                        src: '{,*/}*.{png,jpg,jpeg,gif}',
                        dest: '<%= yeoman.dist %>/images'
                    }, {
                        expand: true,
                        cwd: '<%= yeoman.app %>/img',
                        src: '{,*/}*.{png,jpg,jpeg,gif}',
                        dest: '<%= yeoman.dist %>/img'
                    }]
                }
            },
            svgmin: {
                dist: {
                    files: [{
                        expand: true,
                        cwd: '<%= yeoman.app %>/images',
                        src: '{,*/}*.svg',
                        dest: '<%= yeoman.dist %>/images'
                    }]
                }
            },
            htmlmin: {
                dist: {
                    options: {
                        collapseWhitespace: true,
                        collapseBooleanAttributes: true,
                        removeCommentsFromCDATA: true,
                        removeOptionalTags: true
                    },
                    files: [{
                        expand: true,
                        cwd: '<%= yeoman.dist %>',
                        src: ['*.html', 'views/{,*/, **/}*.html'],
                        dest: '<%= yeoman.dist %>'
                    }]
                }
            },
            // ngmin tries to make the code safe for minification
            // automatically by
            // using the Angular long form for dependency injection. It
            // doesn't work on
            // things like resolve or inject so those have to be done
            // manually.
            ngmin: {
                dist: {
                    files: [{
                        expand: true,
                        cwd: 'app/assets/concat/scripts',
                        src: '*.js',
                        dest: 'app/assets/concat/scripts'
                    }]
                }
            },
            // Replace Google CDN references
            cdnify: {
                dist: {
                    html: ['<%= yeoman.dist %>/*.html']
                }
            },
            // Removing logs (console logs) from production
            removelogging: {
                dist: {
                    src: "<%= yeoman.dist %>'/scripts/main.min.js"
                    // Each file will be overwritten with the output!
                }
            },
            // Copies remaining files to places other tasks can use
            copy: {
                dist: {
                    files: [
                        {
                            expand: true,
                            dot: true,
                            cwd: '<%= yeoman.app %>',
                            dest: '<%= yeoman.dist %>',
                            src: ['*.{ico,png,txt}', '.htaccess',
                                '*.html', 'views/{,*/,**/}*.html',
                                'images/{,*/}*.{webp}', 'fonts/*']
                        }, {
                            // for font-awesome
                            expand: true,
                            dot: true,
                            cwd: 'bower_components/fontawesome',
                            src: ['fonts/*.*'],
                            dest: '<%= yeoman.dist %>'
                        }, {
                            expand: true,
                            cwd: 'app/assets/images',
                            dest: '<%= yeoman.dist %>/images',
                            src: ['generated/*']
                        }]
                },
                styles: {
                    expand: true,
                    cwd: '<%= yeoman.app %>/styles',
                    dest: '<%= yeoman.app %>/assets/styles/',
                    src: '{,*/}*.css'
                },
                scripts: {
                    expand: true,
                    cwd: '<%= yeoman.app %>/scripts',
                    dest: '<%= yeoman.app %>/assets/scripts',
                    src: '{,*/}*.js'
                },
                javascripts: {
                    expand: true,
                    cwd: '.tmp/concat/scripts',
                    dest: '<%= yeoman.dist %>/scripts',
                    src: '*.js'
                },
                concatjs: {
                    expand: true,
                    cwd: '<%= yeoman.app %>/scripts',
                    src: '**/*.js',
                    dest: '.tmp/scripts',
                    ext: '.js'
                },
                fonts: {
                    expand: true,
                    cwd: '<%= yeoman.app %>/styles/themes/default/assets/fonts',
                    dest: '<%= yeoman.dist %>/styles/themes/default/assets/fonts',
                    src: '*.*'
                }
            },
            // Run some tasks in parallel to speed up the build process
            concurrent: {
                server: ['copy:scripts', 'copy:concatjs', 'copy:styles'],
                test: ['copy:scripts', 'copy:styles'],
                dist: ['copy:scripts', 'copy:javascripts', 'copy:styles', 'copy:fonts', 'imagemin',
                    'svgmin']
            },
            // By default, your `index.html`'s <!-- Usemin block --> will
            // take care of
            // minification. These next options are pre-configured if you do
            // not wish
            // to use the Usemin blocks.
            cssmin: {
                dist: {
                    files: {
                        '<%= yeoman.dist %>/styles/main.css': ['<%= yeoman.app %>/styles/{,}*.css']
                    }
                }
            },
            uglify: {
                dist: {
                    options: {
                        mangle: true
                    },
                    files: {
                        '<%= yeoman.dist %>/scripts/vendors.js': ['.tmp/concat/scripts/vendor.js'],
                        '<%= yeoman.dist %>/scripts/main.js': ['.tmp/concat/scripts/scripts.js']
                    }
                }
            },
            concat: {
                dist: {}
            }
        });
    grunt.registerTask('serve',
        function (target) {
            if (target === 'dist') {
                return grunt.task.run(['build', 'connect:dist:keepalive']);
            }
            grunt.task.run(['clean:server', 'bowerInstall', 'concurrent:server',
                'autoprefixer', 'csslint:lax', 'jshint', 'connect:livereload',
                'watch']);
            // ['white', 'black', 'grey', 'blue', 'cyan', 'green', 'magenta', 'red',
            // 'yellow', 'rainbow']
            grunt.log.warn('Server up and running...'['blue'].bold);
        });
    grunt.registerTask('server',
        function (target) {
            grunt.log
                .warn('The `server` task has been deprecated. Use `grunt serve` to start a server.'['yellow'].bold);
            grunt.task.run(['serve:' + target]);
        });
    grunt.registerTask('test', ['clean:server', 'concurrent:test',
        'autoprefixer', 'connect:test', 'karma']);
    grunt.registerTask('build', ['clean:dist', 'bowerInstall',
        'useminPrepare',
        'concurrent:dist',
        'autoprefixer',
        'concat',
        'ngmin',
        'copy:dist',
        'cdnify',
        'cssmin',
        'csslint:lax',
        'jshint',
        // 'copy:javascripts',
        // 'rev',
        'usemin',
        //'uglify',
        'htmlmin'
    ]);
    grunt.registerTask('default', ['newer:jshint', 'test', 'build']);
};