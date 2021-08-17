module.exports = {
    outputDir: "../src/main/resources/static",
    indexPath: "../static/index.html",
    devServer: {
        proxy: {
            '/': {
                "target": 'http://localhost:3000/api',
                "pathRewrite": { '^/': '' },
                "changeOrigin": true,
                "secure": false
            }
        }
    },
    transpileDependencies: [
        'vuetify'
    ]
}
