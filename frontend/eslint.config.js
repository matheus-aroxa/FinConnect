const gtsConfigs = require('gts');

module.exports = [
  // 1. Load Google's foundational configuration rules
  ...gtsConfigs,

  // 2. Patch the parser settings to play nicely with Angular's tsconfig routing
  {
    languageOptions: {
      parserOptions: {
        // Disables the old static file analyzer causing the error
        project: null,
        // Enables the modern service that naturally traverses Angular's project structures
        projectService: true,
        tsconfigRootDir: __dirname,
      },
    },
    rules: {
      // Allows Angular's dependency injection constructor patterns seamlessly
      '@typescript-eslint/no-useless-constructor': 'off',
    },
  },
];
