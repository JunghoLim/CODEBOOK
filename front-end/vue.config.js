module.exports = {
    outputDir: "../src/main/resources/static",
    indexPath: "../static/index.html",
    devServer: {
        port: 8090,
        proxy: {
            '/api': {
                "target": 'http://localhost:3000/api',
                "changeOrigin": true,
                "secure": false
            }
        }
    },
    transpileDependencies: [
        'vuetify'
    ],
}
