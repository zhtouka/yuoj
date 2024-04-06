const { defineConfig } = require("@vue/cli-service");
const MonacoWebpackPlugin = require("monaco-editor-webpack-plugin");
module.exports = defineConfig({
  transpileDependencies: true,
  chainWebpack: (config) => {
    config.plugin("monaco-editor").use(
      new MonacoWebpackPlugin({
        languages: [
          "cpp",
          "go",
          "java",
          "javascript",
          "python",
          "rust",
          "typescript",
        ],
      })
    );
  },
  devServer: {
    port: 8070,
  },
});
