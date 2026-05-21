module.exports = {
  // Inherit Google's foundational rules (like 2-space indentation)
  ...require('gts/.prettierrc.json'),

  // Apply your specific layout rules
  printWidth: 100,
  singleQuote: true,

  // Load the Tailwind CSS sorting engine
  plugins: ['prettier-plugin-tailwindcss'],
  tailwindAttributes: ['class'],

  // Ensure Angular HTML template tags parse cleanly
  overrides: [
    {
      files: '*.html',
      options: {
        parser: 'angular',
      },
    },
  ],
};
