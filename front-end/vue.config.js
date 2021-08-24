module.exports = {
    outputDir: "../src/main/resources/static",
    devServer: {
        port: 8090,
        proxy: {
            '/api': {
                "target": 'http://localhost:3000',
                "changeOrigin": true
            }
        }
    },
    transpileDependencies: [
        'vuetify'
    ],
}