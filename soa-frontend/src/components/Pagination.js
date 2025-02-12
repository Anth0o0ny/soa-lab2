import React from 'react';

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
  return (
    <div>
      <button
        onClick={() => onPageChange(currentPage - 1)}
        disabled={currentPage === 1} // Отключаем кнопку, если текущая страница 1
      >
        Previous
      </button>
      <span>Page {currentPage} of {totalPages}</span>
      <button
        onClick={() => onPageChange(currentPage + 1)}
        disabled={currentPage === totalPages || totalPages === 0} // Отключаем кнопку, если текущая страница последняя или totalPages = 0
      >
        Next
      </button>
    </div>
  );
};

export default Pagination;